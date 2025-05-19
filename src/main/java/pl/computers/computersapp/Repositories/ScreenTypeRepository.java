package pl.computers.computersapp.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.computers.computersapp.Models.ScreenType;

import java.util.Optional;

@Repository
public interface ScreenTypeRepository extends JpaRepository<ScreenType, Long> {
    Optional<ScreenType> findByType(String type);
}
