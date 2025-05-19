package pl.computers.computersapp.Services;

import org.springframework.stereotype.Service;
import pl.computers.computersapp.Models.Processor;
import pl.computers.computersapp.Repositories.ComputerRepository;
import pl.computers.computersapp.Repositories.ProcessorRepository;
import pl.computers.computersapp.Tools.ServiceStrategies;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;

@Service
public class ProcessorsService {
    private final ProcessorRepository processorRepository;

    public ProcessorsService(ProcessorRepository processorRepository) {
        this.processorRepository = processorRepository;
    }

    public Processor createProcessor(BigDecimal freq, String model, int cores, int threads) {
        Optional<Processor> dbProcessor = processorRepository.findByModel(model);
        if (dbProcessor.isPresent()) {
            Processor proc = dbProcessor.get();
            if (proc.getFreq().equals(freq) && proc.getCores() == cores && proc.getThreads() == threads) {
                return proc;
            } else {
                throw new IllegalArgumentException("Processor with model: " + model + " already exists!");
            }
        } else {
            return processorRepository.save(
                    Processor.builder().freq(freq).model(model).cores(cores).threads(threads).build());
        }
    }

    public void deleteProcessor(long id) {
        processorRepository.deleteById(id);
    }

    public Processor updateProcessor(
            long id, BigDecimal freq, String model, int cores, int threads, ComputerRepository computersRepository) {
        try {
            return ServiceStrategies.enumObjectUpdateStrategy(id, computersRepository, processorRepository,
                    Map.of("Freq", freq, "Model", model, "Cores", cores, "Threads", threads));
        } catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException
                 | InstantiationException e) {
            throw new RuntimeException(e);
        }
    }
}
