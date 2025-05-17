package pl.computers.computersapp.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "PROCESSOR")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class Processor {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private long id;
    @Column(name = "FREQUENCY", precision = 4, scale = 2, nullable = false)
    private BigDecimal freq;
    @Column(name = "MODEL", length = 50, nullable = false)
    private String model;
    @Column(name = "CORES", nullable = false, unique = true)
    @Min(1)
    private int cores;
    @Column(name = "THREADS", nullable = false)
    @Min(1)
    private int threads;
}
