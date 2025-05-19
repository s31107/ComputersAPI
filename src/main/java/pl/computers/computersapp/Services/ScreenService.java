package pl.computers.computersapp.Services;


import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import pl.computers.computersapp.Models.Screen;
import pl.computers.computersapp.Models.ScreenType;
import pl.computers.computersapp.Repositories.ScreenRepository;
import pl.computers.computersapp.Repositories.ScreenTypeRepository;
import pl.computers.computersapp.Tools.ServiceStrategies;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.NoSuchElementException;

@Service
public class ScreenService {
    private final ScreenTypeRepository screenTypeRepository;
    private final ScreenRepository screenRepository;

    public ScreenService(ScreenTypeRepository screenTypeRepository, ScreenRepository screenRepository) {
        this.screenTypeRepository = screenTypeRepository;
        this.screenRepository = screenRepository;
    }

    @Transactional
    public Screen createScreen(String type, int resolutionX, int resolutionY) {
        ScreenType screenType = screenTypeRepository.findByType(type).orElseGet(
                () -> screenTypeRepository.save(ScreenType.builder().type(type).build()));
        return screenRepository.save(
                Screen.builder().resolutionX(resolutionX).resolutionY(resolutionY).screenType(
                        screenType).build());
    }

    @Transactional
    public void deleteScreen(long id) {
        ScreenType screenType = screenRepository.findById(id).orElseThrow(() -> new NoSuchElementException(
                "Screen with id: " + id + " not found!")).getScreenType();
        screenRepository.deleteById(id);
        if (screenRepository.findAll().stream().noneMatch(
                screen -> screen.getScreenType().getId() == screenType.getId())) {
            screenTypeRepository.deleteById(screenType.getId());
        }
    }

    @Transactional
    public Screen updateScreen(
            long id, String screenType, int resolutionX, int resolutionY) {
        try {
            Screen screen = screenRepository.getReferenceById(id);
            ScreenType ramTypeObj = ServiceStrategies.enumObjectUpdateStrategy(screen.getScreenType().getId(),
                    screenRepository, screenTypeRepository, Map.of("Type", screenType));
            screen.setScreenType(ramTypeObj);
            screen.setResolutionX(resolutionX);
            screen.setResolutionY(resolutionY);
            return screenRepository.save(screen);
        } catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException
                 | InstantiationException e) {
            throw new RuntimeException(e);
        }
    }
}
