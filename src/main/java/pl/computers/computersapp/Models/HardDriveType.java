package pl.computers.computersapp.Models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "HARD_DRIVE_TYPES")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class HardDriveType {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private long id;
    @Column(name = "TYPE", length = 5, nullable = false, unique = true)
    private String type;
}
