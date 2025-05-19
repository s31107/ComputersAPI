package pl.computers.computersapp.Models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "HARD_DRIVES")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class HardDrive {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private long id;
    @ManyToOne
    @JoinColumn(name = "HARD_DRIVE_TYPE_ID", nullable = false)
    private HardDriveType hardDriveType;
    @Column(name = "CAPACITY", nullable = false)
    private int capacity;
}
