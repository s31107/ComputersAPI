package pl.computers.computersapp.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.computers.computersapp.Models.Processor;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProcessorRepository extends JpaRepository<Processor, Long> {
    Optional<Processor> findByModel(String model);
}
