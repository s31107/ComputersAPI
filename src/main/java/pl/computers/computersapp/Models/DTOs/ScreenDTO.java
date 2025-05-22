package pl.computers.computersapp.Models.DTOs;

import jakarta.validation.constraints.*;

public record ScreenDTO (
    @Min(100)
    @NotNull
    int resolutionX,
    @Min(100)
    @NotNull
    int resolutionY,
    @Size(max = 10)
    @NotBlank
    String screenType
) {}
