package pl.computers.computersapp.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.computers.computersapp.Models.Screen;
import pl.computers.computersapp.Models.ScreenType;

import java.util.Optional;

@Repository
public interface ScreenRepository extends JpaRepository<Screen, Long> {
    Optional<Screen> findByResolutionXAndResolutionYAndScreenType(int resolutionX, int resolutionY, ScreenType screenType);
    long countByScreenType_Id(long screenTypeId);
}
