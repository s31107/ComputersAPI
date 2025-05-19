package pl.computers.computersapp.Repositories;

import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.computers.computersapp.Models.Computer;

import java.util.Optional;

@Repository
public interface ComputerRepository extends JpaRepository<Computer, Long> {
    @Override
    @NonNull Optional<Computer> findById(@NonNull Long id);
    long countByBrand_Id(long brandId);
}
