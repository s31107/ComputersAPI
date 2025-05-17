package pl.computers.computersapp.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.computers.computersapp.Models.Resolution;

@Repository
public interface ResolutionRepository extends JpaRepository<Resolution, Long> {}
