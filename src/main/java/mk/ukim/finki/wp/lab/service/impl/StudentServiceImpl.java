package mk.ukim.finki.wp.lab.service.impl;

import mk.ukim.finki.wp.lab.model.Student;
import mk.ukim.finki.wp.lab.repository.in_memory.InMemoryStudentRepository;
import mk.ukim.finki.wp.lab.repository.jpa.StudentsRepository;
import mk.ukim.finki.wp.lab.service.StudentService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {
    private final StudentsRepository studentRepository;

    public StudentServiceImpl(StudentsRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public List<Student> listAll() {
        return this.studentRepository.findAll();
    }

    @Override
    public List<Student> searchByNameOrSurname(String name, String surname) {
        return this.studentRepository.findAllByNameOrSurname(name, surname);
    }

    @Override
    public Student save(String username, String password, String name, String surname) {
        return this.studentRepository.save(new Student(username,password,name,surname));
    }

    @Override //3
    public List<Student> getFilteredStudents(List<Student> filterList) {
        List<Student> allStudents=this.listAll();
        List<Student> filtered= this.listAll();
        for(int i=0; i<allStudents.size(); i++){
            int finalI = i;
            if(filterList.stream().anyMatch(filteredStudent->filteredStudent.getUsername().equals(allStudents.get(finalI).getUsername()))){
                filtered.remove(allStudents.get(i));
            }
        }
        return filtered;
    }

    @Override
    public Student getByUsername(String username) {
        return this.studentRepository.findById(username).orElse(null);
    }
}
