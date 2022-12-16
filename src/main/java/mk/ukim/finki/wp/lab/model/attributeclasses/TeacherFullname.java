package mk.ukim.finki.wp.lab.model.attributeclasses;


import lombok.Data;

import java.io.Serializable;

@Data
public class TeacherFullname implements Serializable {
    private String name;
    private  String surname;

    public TeacherFullname() {
    }

    public TeacherFullname(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }
}