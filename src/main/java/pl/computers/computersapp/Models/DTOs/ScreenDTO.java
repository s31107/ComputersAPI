package pl.computers.computersapp.Models.DTOs;

import jakarta.validation.constraints.*;

public record ScreenDTO (
    @Max(1080)
    @NotNull
    int screenResolutionX,
    @Min(480)
    @NotNull
    int screenResolutionY,
    @Size(max = 10)
    @NotBlank
    String screenType
) {}
