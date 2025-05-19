package pl.computers.computersapp.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.computers.computersapp.Models.Ram;
import pl.computers.computersapp.Models.RamType;

import java.util.Optional;

@Repository
public interface RamRepository extends JpaRepository<Ram, Long> {
    Optional<Ram> findByRamNumberAndRamType(int ramNumber, RamType ramType);
    long countByRamType_Id(long ramTypeId);
}
