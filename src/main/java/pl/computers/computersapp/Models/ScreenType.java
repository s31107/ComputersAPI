package pl.computers.computersapp.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "SCREEN_TYPES")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class ScreenType {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name = "TYPE", length = 10, nullable = false, unique = true)
    private String type;
}
