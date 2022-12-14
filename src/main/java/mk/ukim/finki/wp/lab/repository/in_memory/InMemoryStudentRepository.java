package mk.ukim.finki.wp.lab.repository.in_memory;

import mk.ukim.finki.wp.lab.model.Student;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Repository
public class InMemoryStudentRepository {
    private final static List<Student> studentList = new ArrayList<>();

    @PostConstruct
    public void init() {
        studentList.add(new Student("sd", "stefanija", "Stefanija", "Duracoska"));
        studentList.add(new Student("tl", "teodora", "Teodora", "Lozankoska"));
        studentList.add(new Student("mg", "magdalena", "Magdalena", "Georgieva"));
        studentList.add(new Student("td", "teodor", "Teodor", "Duracoski"));
        studentList.add(new Student("bd", "biljana", "Biljana", "Duracoska"));
    }

    public List<Student> findAllStudents() {
        return studentList;
    }

    public List<Student> findAllByNameOrSurname(String text) {
        List<Student> filteredStudents = new ArrayList<>();
        for (Student student : studentList) {
            if (student.getName().contains(text) || student.getSurname().contains(text))
                filteredStudents.add(student);
        }
        return filteredStudents;
    }

    public Student save(Student student) {
        studentList.add(student);
        return student;
    }

    public Student findByUsername(String studentUsername) {
        return studentList.stream().filter(student -> student.getUsername().equals(studentUsername)).findFirst().orElse(null);
    }
}
