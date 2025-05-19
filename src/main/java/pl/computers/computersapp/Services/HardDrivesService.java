package pl.computers.computersapp.Services;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import pl.computers.computersapp.Models.HardDrive;
import pl.computers.computersapp.Models.HardDriveType;
import pl.computers.computersapp.Repositories.HardDriveRepository;
import pl.computers.computersapp.Repositories.HardDriveTypeRepository;
import pl.computers.computersapp.Tools.ServiceStrategies;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.NoSuchElementException;

@Service
public class HardDrivesService {
    private final HardDriveRepository hardDriveRepository;
    private final HardDriveTypeRepository hardDriveTypeRepository;

    public HardDrivesService(HardDriveRepository hardDriveRepository, HardDriveTypeRepository hardDriveTypeRepository) {
        this.hardDriveRepository = hardDriveRepository;
        this.hardDriveTypeRepository = hardDriveTypeRepository;
    }

    @Transactional
    public HardDrive createHardDrive(String type, int capacity) {
        HardDriveType hardDriveType = hardDriveTypeRepository.findByType(type).orElseGet(
                () -> hardDriveTypeRepository.save(HardDriveType.builder().type(type).build()));
        return hardDriveRepository.save(HardDrive.builder().capacity(capacity).hardDriveType(hardDriveType).build());
    }

    @Transactional
    public void deleteHardDrive(long id) {
        HardDriveType hardDriveType = hardDriveRepository.findById(id).orElseThrow(() -> new NoSuchElementException(
                "Hard drive with id: " + id + " not found!" )).getHardDriveType();
        hardDriveRepository.deleteById(id);
        if (hardDriveRepository.findAll().stream().noneMatch(
                hd -> hd.getHardDriveType().getId() == hardDriveType.getId())) {
            hardDriveTypeRepository.deleteById(hardDriveType.getId());
        }
    }

    @Transactional
    public HardDrive updateHardDrive(
            long id, int hardDriveCapacity, String hardDriveType) {
        try {
            HardDrive hd = hardDriveRepository.getReferenceById(id);
            HardDriveType hdType = ServiceStrategies.enumObjectUpdateStrategy(hd.getHardDriveType().getId(),
                    hardDriveRepository, hardDriveTypeRepository, Map.of("Type", hardDriveType));
            hd.setHardDriveType(hdType);
            hd.setCapacity(hardDriveCapacity);
            return hardDriveRepository.save(hd);
        } catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException
                 | InstantiationException e) {
            throw new RuntimeException(e);
        }
    }
}
