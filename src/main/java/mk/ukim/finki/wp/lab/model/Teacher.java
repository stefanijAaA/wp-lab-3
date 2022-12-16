package mk.ukim.finki.wp.lab.model;

import lombok.Data;
import mk.ukim.finki.wp.lab.model.attributeclasses.TeacherFullname;
import mk.ukim.finki.wp.lab.model.converters.TeacherFullnameConverter;
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
    @Convert(converter = TeacherFullnameConverter.class)
    private TeacherFullname fullName;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate dateOfEmployment;

    public Teacher(String name, String surname) {
        this.fullName=new TeacherFullname(name, surname);
    }


    public Teacher() {}

}
