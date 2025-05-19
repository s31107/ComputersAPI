package pl.computers.computersapp.Models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "COMPUTERS")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Computer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private long id;
    @Column(name = "COMPUTER_NAME", length = 50, nullable = false)
    private String computerName;
    @ManyToOne
    @JoinColumn(name = "BRAND_ID")
    private Brand brand;
    @ManyToOne
    @JoinColumn(name = "HARD_DRIVE_ID")
    private HardDrive hardDrive;
    @ManyToOne
    @JoinColumn(name = "PROCESSOR_ID")
    private Processor processor;
    @ManyToOne
    @JoinColumn(name = "SCREEN_ID")
    private Screen screen;
    @ManyToOne
    @JoinColumn(name = "RAM_ID")
    private Ram ram;
}
