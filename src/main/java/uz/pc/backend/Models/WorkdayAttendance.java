package uz.pc.backend.Models;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name="workdayattendances")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WorkdayAttendance {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Nullable
    @Column(name = "arrival_time")
    private LocalTime arrivalTime;

    @Nullable
    @Column(name = "departure_time")
    private LocalTime departTime;

    @Nullable
    @Column(name = "arrival_date")
    private LocalDate arrivalDate;

    @Nullable
    @Column(name = "work_hour")
    private long workHour;

    @Column(name = "emp_card_id")
    private String empCardId;

    @Nullable
    @Column(name = "weekday")
    private String weekday;

    @Nullable
    @Column(name = "arrival_difference")
    private String arrivalDifference;

    @Nullable
    @Column(name = "departure_difference")
    private String departureDifference;

    @Column(name = "is_worked")
    private boolean worked = false;
}

