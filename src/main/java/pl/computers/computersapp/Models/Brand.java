package pl.computers.computersapp.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "BRANDS")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name = "NAME", length = 50, nullable = false, unique = true)
    private String name;
}
