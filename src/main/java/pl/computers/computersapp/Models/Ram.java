package pl.computers.computersapp.Models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "RAM")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Ram {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private long id;
    @Column(name = "RAM_NUMBER", nullable = false)
    private int ramNumber;
    @ManyToOne
    @JoinColumn(name = "RAM_TYPE_ID", nullable = false)
    private RamType ramType;
}
