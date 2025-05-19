package pl.computers.computersapp.Services;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import pl.computers.computersapp.Models.Ram;
import pl.computers.computersapp.Models.RamType;
import pl.computers.computersapp.Repositories.RamRepository;
import pl.computers.computersapp.Repositories.RamTypeRepository;
import pl.computers.computersapp.Tools.ServiceStrategies;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.NoSuchElementException;

@Service
public class RamsService {
    private final RamRepository ramRepository;
    private final RamTypeRepository ramTypeRepository;

    public RamsService(RamRepository ramService, RamTypeRepository ramTypeRepository) {
        this.ramRepository = ramService;
        this.ramTypeRepository = ramTypeRepository;
    }

    @Transactional
    public Ram createRam(String ramType, int ramNumber) {
        RamType ramTypeObj = ramTypeRepository.findByType(ramType).orElseGet(() -> ramTypeRepository.save(
                RamType.builder().type(ramType).build()));
        return ramRepository.save(Ram.builder().ramNumber(ramNumber).ramType(ramTypeObj).build());
    }

    @Transactional
    public void deleteRam(long id) {
        RamType ramType = ramRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("Ram with id: " + id + " not found!")).getRamType();
        ramRepository.deleteById(id);
        if (ramRepository.findAll().stream().noneMatch(
                ram -> ram.getRamType().getId() == ramType.getId())) {
            ramTypeRepository.deleteById(ramType.getId());
        }
    }

    @Transactional
    public Ram updateRam(long id, String ramType, int ramNumber) {
        try {
            Ram ram = ramRepository.getReferenceById(id);
            RamType ramTypeObj = ServiceStrategies.enumObjectUpdateStrategy(ram.getRamType().getId(), ramRepository,
                    ramTypeRepository, Map.of("Type", ramType));
            ram.setRamType(ramTypeObj);
            ram.setRamNumber(ramNumber);
            return ramRepository.save(ram);
        } catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException
                 | InstantiationException e) {
            throw new RuntimeException(e);
        }
    }
}
