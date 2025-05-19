package pl.computers.computersapp.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.computers.computersapp.Models.HardDrive;
import pl.computers.computersapp.Models.HardDriveType;

import java.util.Optional;

@Repository
public interface HardDriveRepository extends JpaRepository<HardDrive, Long> {
    Optional<HardDrive> findByCapacityAndHardDriveType(int capacity, HardDriveType hardDriveType);
    long countByHardDriveType_Id(long hardDriveTypeId);
}
