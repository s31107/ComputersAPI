package pl.computers.computersapp.Models.DTOs;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record ProcessorDTO (
    @Min(1)
    @NotNull
    BigDecimal freq,
    @NotBlank
    @Size(max = 50)
    String model,
    @Min(1)
    @NotNull
    int cores,
    @Min(1)
    @NotNull
    int threads
) {}
