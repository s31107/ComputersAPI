package pl.computers.computersapp.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.computers.computersapp.Models.HardDrive;

@Repository
public interface HardDriveRepository extends JpaRepository<HardDrive, Long> {}
