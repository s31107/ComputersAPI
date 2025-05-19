package pl.computers.computersapp.Models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "RAM_TYPES")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class RamType {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private long id;
    @Column(name = "TYPE", length = 10, nullable = false, unique = true)
    private String type;
}
