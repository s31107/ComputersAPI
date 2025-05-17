package pl.computers.computersapp.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.computers.computersapp.Models.RamType;

@Repository
public interface RamTypeRepository extends JpaRepository<RamType, Long> {}
