package pl.computers.computersapp.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.computers.computersapp.Models.HardDriveType;

import java.util.Optional;

@Repository
public interface HardDriveTypeRepository extends JpaRepository<HardDriveType, Long> {
    Optional<HardDriveType> findByType(String type);
}
