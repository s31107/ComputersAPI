package pl.computers.computersapp.Services;

import org.springframework.stereotype.Service;
import pl.computers.computersapp.Models.Brand;
import pl.computers.computersapp.Repositories.BrandRepository;
import pl.computers.computersapp.Repositories.ComputerRepository;
import pl.computers.computersapp.Tools.ServiceStrategies;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@Service
public class BrandsService {
    private final BrandRepository brandRepository;

    public BrandsService(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    public Brand createBrand(String name) {
        return brandRepository.findByName(name).orElseGet(() -> brandRepository.save(
                Brand.builder().name(name).build()));
    }

    public void deleteBrand(long id, ComputerRepository computersRepository) {
        if (computersRepository.findAll().stream().noneMatch(
                computer -> computer.getBrand().getId() == id)) {
            brandRepository.deleteById(id);
        }
    }

    public Brand updateBrand(long id, String name, ComputerRepository computersRepository) {
        try {
            return ServiceStrategies.enumObjectUpdateStrategy(id, computersRepository, brandRepository,
                    Map.of("Name", name));
        } catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException
                 | InstantiationException e) {
            throw new RuntimeException(e);
        }
    }
}
