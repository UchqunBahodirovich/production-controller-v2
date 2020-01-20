package uz.pc.backend.Models;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.time.LocalTime;

@Entity
@Table(name="settings")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Setting {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Nullable
    @Column(name = "arrival_time")
    private LocalTime arrivalTime;

    @Nullable
    @Column(name = "departure_time")
    private LocalTime departureTime;


}
