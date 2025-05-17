package pl.computers.computersapp.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "COMPUTERS")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class Computer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private long id;
    @Column(name = "RAM_NUMBER", nullable = false)
    @Min(1)
    private int ramNumber;
    @ManyToOne
    @JoinColumn(name = "BRAND_ID")
    private Brand brand;
    @ManyToOne
    @JoinColumn(name = "HARD_DRICE_id")
    private HardDrive hardDrive;
    @ManyToOne
    @JoinColumn(name = "PROCESSOR_ID")
    private Processor processor;
    @ManyToOne
    @JoinColumn(name = "SCREEN_TYPE_ID")
    private ScreenType screenType;
    @ManyToOne
    @JoinColumn(name = "RESOLUTION_ID")
    private Resolution resolution;
    @ManyToOne
    @JoinColumn(name = "RAM_TYPE_ID")
    private RamType ramType;
}
