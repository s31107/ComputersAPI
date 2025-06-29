package pl.computers.computersapp.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.computers.computersapp.Models.RamType;

import java.util.Optional;

@Repository
public interface RamTypeRepository extends JpaRepository<RamType, Long> {
    Optional<RamType> findByType(String type);
}
