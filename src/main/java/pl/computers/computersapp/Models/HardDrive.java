package pl.computers.computersapp.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "HARD_DRIVES")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class HardDrive {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private long id;
    @Column(name = "TYPE", length = 5, nullable = false, unique = true)
    private String type;
}
