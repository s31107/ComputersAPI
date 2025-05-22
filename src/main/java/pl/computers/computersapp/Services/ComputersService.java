package pl.computers.computersapp.Services;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.computers.computersapp.Models.*;
import pl.computers.computersapp.Models.DTOs.*;
import pl.computers.computersapp.Repositories.ComputerRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

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

        HardDriveDTO hardDriveDTO = Optional.ofNullable(hardDrive)
                .map(hd -> new HardDriveDTO(hd.getHardDriveType().getType(), hd.getCapacity()))
                .orElse(null);
        ProcessorDTO processorDTO = Optional.ofNullable(processor)
                .map(p -> new ProcessorDTO(p.getFreq(), p.getModel(), p.getCores(), p.getThreads()))
                .orElse(null);
        RamDTO ramDTO = Optional.ofNullable(ram)
                .map(r -> new RamDTO(r.getRamNumber(), r.getRamType().getType()))
                .orElse(null);
        ScreenDTO screenDTO = Optional.ofNullable(screen)
                .map(s -> new ScreenDTO(s.getResolutionX(), s.getResolutionY(), s.getScreenType().getType()))
                .orElse(null);

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
        HardDrive hardDrive = Optional.ofNullable(computerDTO.hardDrive()).map(hardDriveDto ->
                hardDrivesService.createHardDrive(hardDriveDto.hardDriveType(),
                        hardDriveDto.capacity())).orElse(null);
        Processor processor = Optional.ofNullable(computerDTO.processor()).map(
                processorDto -> processorsService.createProcessor(processorDto.freq(),
                        processorDto.model(), processorDto.cores(), processorDto.threads())).orElse(null);
        Ram ram = Optional.ofNullable(computerDTO.ram()).map(ramDto -> ramsService.createRam(ramDto.ramType(),
                ramDto.ramNumber())).orElse(null);
        Screen screen = Optional.ofNullable(computerDTO.screen()).map(screenDto ->
                screenService.createScreen(screenDto.screenType(), screenDto.resolutionX(),
                        screenDto.resolutionY())).orElse(null);
        return mapComputerToDTO(computerRepository.save(Computer.builder().brand(brand).computerName(
                computerDTO.computerName()).hardDrive(hardDrive).processor(processor).ram(ram).screen(
                        screen).build()));
    }

    public void updateComputer(ComputerPutDTO computerDTO) {
        Computer computer = computerRepository.findById(computerDTO.id()).orElseThrow(
                () -> new NoSuchElementException("Computer with id: " + computerDTO.id() + " not found! "));
        computer.setComputerName(computerDTO.computerName());
        computer.setBrand(brandsService.updateBrand(computer.getBrand().getId(), computerDTO.brandName(),
                computerRepository));

        Optional.ofNullable(computerDTO.hardDrive()).ifPresentOrElse(
                dto -> Optional.ofNullable(computer.getHardDrive())
                        .map(existing -> hardDrivesService.updateHardDrive(
                                existing.getId(), dto.capacity(), dto.hardDriveType()))
                        .or(() -> Optional.of(hardDrivesService.createHardDrive(dto.hardDriveType(), dto.capacity())))
                        .ifPresent(computer::setHardDrive),
                () -> Optional.ofNullable(computer.getHardDrive())
                        .ifPresent(existing -> {
                            hardDrivesService.deleteHardDrive(existing.getId());
                            computer.setHardDrive(null);
                        })
        );
        Optional.ofNullable(computerDTO.processor()).ifPresentOrElse(
                dto -> Optional.ofNullable(computer.getProcessor())
                        .map(existing -> processorsService.updateProcessor(existing.getId(),
                                dto.freq(), dto.model(), dto.cores(), dto.threads()))
                        .or(() -> Optional.of(processorsService.createProcessor(
                                dto.freq(), dto.model(), dto.cores(), dto.threads())))
                        .ifPresent(computer::setProcessor),
                () -> Optional.ofNullable(computer.getProcessor())
                        .ifPresent(existing -> {
                            processorsService.deleteProcessor(existing.getId());
                            computer.setProcessor(null);
                        })
        );
        Optional.ofNullable(computerDTO.ram()).ifPresentOrElse(
                dto -> Optional.ofNullable(computer.getRam())
                        .map(existing -> ramsService.updateRam(existing.getId(), dto.ramType(), dto.ramNumber()))
                        .or(() -> Optional.of(ramsService.createRam(dto.ramType(), dto.ramNumber())))
                        .ifPresent(computer::setRam),
                () -> Optional.ofNullable(computer.getRam())
                        .ifPresent(existing -> {
                            ramsService.deleteRam(existing.getId());
                            computer.setRam(null);
                        })
        );
        Optional.ofNullable(computerDTO.screen()).ifPresentOrElse(
                dto -> Optional.ofNullable(computer.getScreen())
                        .map(existing -> screenService.updateScreen(existing.getId(),
                                dto.screenType(), dto.resolutionX(), dto.resolutionY()))
                        .or(() -> Optional.of(screenService.createScreen(
                                dto.screenType(), dto.resolutionX(), dto.resolutionY())))
                        .ifPresent(computer::setScreen),
                () -> Optional.ofNullable(computer.getScreen())
                        .ifPresent(existing -> {
                            screenService.deleteScreen(existing.getId());
                            computer.setScreen(null);
                        })
        ); computerRepository.save(computer);
    }

    @Transactional
    public void deleteComputer(long id) {
        Computer computer = computerRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("Computer with id: " + id + " not found! "));
        computerRepository.deleteById(id);
        brandsService.deleteBrand(computer.getBrand().getId(), computerRepository);

        Optional.ofNullable(computer.getHardDrive())
                .ifPresent(hd -> hardDrivesService.deleteHardDrive(hd.getId()));
        Optional.ofNullable(computer.getHardDrive())
                .ifPresent(hd -> hardDrivesService.deleteHardDrive(hd.getId()));
        Optional.ofNullable(computer.getProcessor())
                .ifPresent(proc -> processorsService.deleteProcessor(proc.getId()));
        Optional.ofNullable(computer.getRam())
                .ifPresent(ram -> ramsService.deleteRam(ram.getId()));
        Optional.ofNullable(computer.getScreen())
                .ifPresent(screen -> screenService.deleteScreen(screen.getId()));
    }
}
