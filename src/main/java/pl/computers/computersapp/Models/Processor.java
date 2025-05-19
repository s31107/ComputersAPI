package pl.computers.computersapp.Models;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "PROCESSOR")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Processor {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private long id;
    @Column(name = "FREQUENCY", precision = 4, scale = 2, nullable = false)
    private BigDecimal freq;
    @Column(name = "MODEL", length = 50, nullable = false, unique = true)
    private String model;
    @Column(name = "CORES", nullable = false)
    private int cores;
    @Column(name = "THREADS", nullable = false)
    private int threads;
}
