package pl.computers.computersapp.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.computers.computersapp.Models.Brand;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {}
