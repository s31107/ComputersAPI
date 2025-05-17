package pl.computers.computersapp.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "RESOLUTIONS")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class Resolution {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private long id;
    @Column(name = "RESOLUTION_X", nullable = false)
    @Min(720)
    private int resolutionX;
    @Column(name = "RESOLUTION_Y", nullable = false)
    @Min(480)
    private int resolutionY;
}
