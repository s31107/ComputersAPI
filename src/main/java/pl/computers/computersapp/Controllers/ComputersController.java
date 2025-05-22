package pl.computers.computersapp.Controllers;

import jakarta.persistence.OptimisticLockException;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.computers.computersapp.Models.DTOs.ComputerDTO;
import pl.computers.computersapp.Models.DTOs.ComputerPutDTO;
import pl.computers.computersapp.Services.ComputersService;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/data")
public class ComputersController {
    private final ComputersService computersService;

    public ComputersController(ComputersService computersService) {
        this.computersService = computersService;
    }

    @GetMapping("/computers")
    public ResponseEntity<?> getAllComputers(Pageable pageable) {
        try {
            return ResponseEntity.ok(computersService.getAllComputers(pageable));
        } catch (PropertyReferenceException exc) {
            return new ResponseEntity<>(exc.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping ("/computers/{id}")
    public ResponseEntity<?> getComputerById(@PathVariable long id) {
        try {
            return ResponseEntity.ok(computersService.getComputerById(id));
        } catch (NoSuchElementException exc) {
            return new ResponseEntity<>(exc.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @PostMapping("/addComputer")
    public ResponseEntity<?> createComputer(@RequestBody @Valid ComputerDTO computerDTO) {
        try {
            return ResponseEntity.ok(computersService.createComputer(computerDTO));
        } catch (NoSuchElementException | IllegalArgumentException exc) {
            return new ResponseEntity<>(exc.getMessage(), HttpStatus.CONFLICT);
        } catch (Exception exc) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/updateComputer")
    public ResponseEntity<String> updateComputer(@RequestBody @Valid ComputerPutDTO computerDTO) {
        try {
            computersService.updateComputer(computerDTO);
        } catch (NoSuchElementException | IllegalArgumentException | OptimisticLockException exc) {
            return new ResponseEntity<>(exc.getMessage(), HttpStatus.CONFLICT);
        } catch (Exception exc) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        } return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/deleteComputer/{id}/{version}")
    public ResponseEntity<String> deleteComputer(@PathVariable long id, @PathVariable long version) {
        try {
            computersService.deleteComputer(id, version);
        } catch (NoSuchElementException | OptimisticLockException exc) {
            return new ResponseEntity<>(exc.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception exc) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        } return ResponseEntity.noContent().build();
    }
}
