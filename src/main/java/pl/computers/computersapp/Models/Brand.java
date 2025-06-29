package pl.computers.computersapp.Models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "BRANDS")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private long id;
    @Column(name = "NAME", length = 50, nullable = false, unique = true)
    private String name;
}
