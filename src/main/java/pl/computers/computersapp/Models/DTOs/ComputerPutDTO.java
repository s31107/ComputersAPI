package pl.computers.computersapp.Models.DTOs;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ComputerPutDTO(
        long id,
        @NotBlank
        @Size(max = 50)
        String brandName,
        @NotBlank
        @Size(max = 50)
        String computerName,

        @Valid
        HardDriveDTO hardDrive,
        @Valid
        ProcessorDTO processor,
        @Valid
        RamDTO ram,
        @Valid
        ScreenDTO screen,
        @NotNull
        long version
) {}
