package pl.computers.computersapp.Models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "SCREEN")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Screen {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private long id;
    @Column(name = "RESOLUTION_X", nullable = false)
    private int resolutionX;
    @Column(name = "RESOLUTION_Y", nullable = false)
    private int resolutionY;
    @ManyToOne
    @JoinColumn(name = "SCREEN_TYPE_ID", nullable = false)
    private ScreenType screenType;
}
