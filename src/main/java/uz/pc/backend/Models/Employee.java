package uz.pc.backend.Models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.persistence.*;

@Entity
@Table(name="employees")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Nullable
    @Column(name = "first_name")
    private String firstName;

    @Nullable
    @Column(name = "last_name")
    private String lastName;

    @Nullable
    @Column(name="position")
    private String position;

    @Nullable
    @Column(name = "identification")
    private String identification;


}

