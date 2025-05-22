package pl.computers.computersapp.Models.DTOs;

public record ComputerGetDTO (
    long id,
    String brandName,
    String computerName,

    HardDriveDTO hardDrive,
    ProcessorDTO processor,
    RamDTO ram,
    ScreenDTO screen,
    long version
) {}
