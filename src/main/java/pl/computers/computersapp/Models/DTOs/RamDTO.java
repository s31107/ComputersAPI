package pl.computers.computersapp.Models.DTOs;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RamDTO (
    @Min(1)
    @NotNull
    int ramNumber,
    @NotBlank
    @Size(max = 10)
    String ramType
) {}
