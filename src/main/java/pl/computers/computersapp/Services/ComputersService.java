package pl.computers.computersapp.Services;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pl.computers.computersapp.Models.*;
import pl.computers.computersapp.Models.DTOs.*;
import pl.computers.computersapp.Repositories.ComputerRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ComputersService {
    private final ComputerRepository computerRepository;
    private final BrandsService brandsService;
    private final HardDrivesService hardDrivesService;
    private final ProcessorsService processorsService;
    private final RamsService ramsService;
    private final ScreenService screenService;

    public ComputersService(ComputerRepository computerRepository, BrandsService brandsService,
                            HardDrivesService hardDrivesService, ProcessorsService processorsService,
                            RamsService ramsService, ScreenService screensService) {
        this.computerRepository = computerRepository;
        this.brandsService = brandsService;
        this.hardDrivesService = hardDrivesService;
        this.processorsService = processorsService;
        this.ramsService = ramsService;
        this.screenService = screensService;
    }

    private static ComputerGetDTO mapComputerToDTO(Computer computer) {
        HardDrive hardDrive = computer.getHardDrive();
        Processor processor = computer.getProcessor();
        Ram ram = computer.getRam();
        Screen screen = computer.getScreen();
        ScreenType screenType = screen.getScreenType();

        HardDriveDTO hardDriveDTO = new HardDriveDTO(
                hardDrive.getHardDriveType().getType(), hardDrive.getCapacity());
        ProcessorDTO processorDTO = new ProcessorDTO(
                processor.getFreq(), processor.getModel(), processor.getCores(), processor.getThreads());
        RamDTO ramDTO = new RamDTO(computer.getRam().getRamNumber(), ram.getRamType().getType());
        ScreenDTO screenDTO = new ScreenDTO(
                screen.getResolutionX(), screen.getResolutionY(), screenType.getType());

        return new ComputerGetDTO(computer.getId(), computer.getBrand().getName(), computer.getComputerName(),
                hardDriveDTO, processorDTO, ramDTO, screenDTO);
    }

    public List<ComputerGetDTO> getAllComputers(Pageable pageable) {
        return computerRepository.findAll(pageable).stream().map(ComputersService::mapComputerToDTO).toList();
    }


    public ComputerGetDTO getComputerById(long id) {
        return mapComputerToDTO(computerRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("Computer of id: " + id + " not found!")));
    }

    @Transactional
    public ComputerGetDTO createComputer(ComputerDTO computerDTO) {
        Brand brand = brandsService.createBrand(computerDTO.brandName());
        HardDrive hardDrive = hardDrivesService.createHardDrive(computerDTO.hardDrive().hardDriveType(),
                computerDTO.hardDrive().hardDriveCapacity());
        Processor processor = processorsService.createProcessor(computerDTO.processor().processorFreq(),
                computerDTO.processor().processorModel(), computerDTO.processor().processorCores(),
                computerDTO.processor().processorThreads());
        Ram ram = ramsService.createRam(computerDTO.ram().ramType(), computerDTO.ram().ramNumber());
        Screen screen = screenService.createScreen(computerDTO.screen().screenType(),
                computerDTO.screen().screenResolutionX(), computerDTO.screen().screenResolutionY());
        return mapComputerToDTO(computerRepository.save(Computer.builder().brand(brand).computerName(
                computerDTO.computerName()).hardDrive(hardDrive).processor(processor).ram(ram).screen(
                        screen).build()));
    }

    public void updateComputer(ComputerGetDTO computerDTO) {
        Computer computer = computerRepository.findById(computerDTO.id()).orElseThrow(
                () -> new NoSuchElementException("Computer with id: " + computerDTO.id() + " not found! "));
        computer.setComputerName(computerDTO.computerName());
        computer.setBrand(brandsService.updateBrand(computer.getBrand().getId(), computerDTO.brandName(),
                computerRepository));
        computer.setHardDrive(hardDrivesService.updateHardDrive(computer.getHardDrive().getId(),
                computerDTO.hardDrive().hardDriveCapacity(), computerDTO.hardDrive().hardDriveType()));
        computer.setProcessor(processorsService.updateProcessor(computer.getProcessor().getId(),
                computerDTO.processor().processorFreq(), computerDTO.processor().processorModel(),
                computerDTO.processor().processorCores(), computerDTO.processor().processorThreads(),
                computerRepository));
        computer.setRam(ramsService.updateRam(computer.getRam().getId(), computerDTO.ram().ramType(),
                computerDTO.ram().ramNumber()));
        computer.setScreen(screenService.updateScreen(computer.getScreen().getId(),
                computerDTO.screen().screenType(), computerDTO.screen().screenResolutionX(),
                computerDTO.screen().screenResolutionY()));
        computerRepository.save(computer);
    }

    @Transactional
    public void deleteComputer(long id) {
        Computer computer = computerRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("Computer with id: " + id + " not found! "));
        computerRepository.deleteById(id);
        brandsService.deleteBrand(computer.getBrand().getId(), computerRepository);
        hardDrivesService.deleteHardDrive(computer.getHardDrive().getId());
        processorsService.deleteProcessor(computer.getProcessor().getId());
        ramsService.deleteRam(computer.getRam().getId());
        screenService.deleteScreen(computer.getScreen().getId());
    }
}
