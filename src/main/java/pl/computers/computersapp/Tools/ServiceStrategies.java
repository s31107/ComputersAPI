package pl.computers.computersapp.Tools;

import org.springframework.data.jpa.repository.JpaRepository;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

public class ServiceStrategies {
    public static <T, E> T enumObjectUpdateStrategy(
            long id, JpaRepository<E, Long> parentRepository, JpaRepository<T, Long> repository,
            Map<String, Object> args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException,
            InstantiationException {
        T dbInstance = repository.findById(id).orElseThrow(() -> new NoSuchElementException(
                "Record with id: " + id + " not found!"));

        Class<?> dbClass = dbInstance.getClass();
        Class<?> repositoryClass = repository.getClass();
        Class<?> parentRepositoryClass = parentRepository.getClass();
        boolean sameUpdate = true;
        for (Map.Entry<String, Object> arg : args.entrySet()) {
            if (!dbClass.getMethod("get" + arg.getKey()).invoke(dbInstance).equals(arg.getValue())) {
                sameUpdate = false;
                break;
            }
        }
        if (sameUpdate) { return dbInstance; }

        StringBuilder methodName = new StringBuilder("findBy");
        Object[] methodArgs = new Object[args.size()];
        Class<?>[] classMethodArgs = new Class<?>[args.size()];
        int iter = 0;
        for (String arg : args.keySet()) {
            methodName.append(arg).append("And");
            methodArgs[iter] = args.get(arg);
            classMethodArgs[iter] = methodArgs[iter++].getClass();
        } methodName = new StringBuilder(methodName.substring(0, methodName.length() - "And".length()));

        @SuppressWarnings("unchecked")
        Optional<T> existing = (Optional<T>) repositoryClass.getMethod(methodName.toString(), classMethodArgs).invoke(
                repository, methodArgs);

        if (!parentRepositoryClass.getMethod("countBy" + dbClass.getSimpleName() + "_Id", long.class).invoke(
                parentRepository, id).equals(1L)) {
            if (existing.isPresent()) { return existing.get(); }
            @SuppressWarnings("unchecked")
            T nObj = (T) dbClass.getConstructor().newInstance();
            for (Map.Entry<String, Object> arg : args.entrySet()) {
                dbClass.getMethod("set" + arg.getKey(), arg.getValue().getClass()).invoke(nObj, arg.getValue());
            }
            return repository.save(nObj);
        } else {
            if (existing.isPresent()) {
                repository.deleteById(id);
                return existing.get();
            } else {
                for (Map.Entry<String, Object> arg : args.entrySet()) {
                    dbClass.getMethod("set" + arg.getKey(), arg.getValue().getClass()).invoke(
                            dbInstance, arg.getValue());
                }
                return repository.save(dbInstance);
            }
        }
    }
}
