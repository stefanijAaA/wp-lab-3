package mk.ukim.finki.wp.lab.model;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "teachers")
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String surname;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate dateOfEmployment;
    public Teacher(String name, String surname) {
        this.id=(long)(Math.random()*1000);
        this.name = name;
        this.surname = surname;
    }


    public Teacher() {}

}
