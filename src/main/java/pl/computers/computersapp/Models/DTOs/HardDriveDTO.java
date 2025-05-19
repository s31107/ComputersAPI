package pl.computers.computersapp.Models.DTOs;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record HardDriveDTO (
    @NotBlank
    @Size(max = 5)
    String hardDriveType,
    @NotNull
    @Min(1)
    int hardDriveCapacity
) {}
