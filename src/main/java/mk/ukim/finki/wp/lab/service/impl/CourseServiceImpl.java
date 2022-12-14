package mk.ukim.finki.wp.lab.service.impl;

import mk.ukim.finki.wp.lab.model.Course;
import mk.ukim.finki.wp.lab.model.Student;
import mk.ukim.finki.wp.lab.model.Teacher;
import mk.ukim.finki.wp.lab.repository.in_memory.InMemoryCourseRepository;
import mk.ukim.finki.wp.lab.repository.in_memory.InMemoryStudentRepository;
import mk.ukim.finki.wp.lab.repository.in_memory.InMemoryTeacherRepository;
import mk.ukim.finki.wp.lab.repository.jpa.CourseRepository;
import mk.ukim.finki.wp.lab.repository.jpa.StudentsRepository;
import mk.ukim.finki.wp.lab.repository.jpa.TeacherRepository;
import mk.ukim.finki.wp.lab.service.CourseService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CourseServiceImpl implements CourseService {
    private final CourseRepository courseRepository; //SpringBoot injects this repository into CourseService(dependencyInjection by constructor)
    private final StudentsRepository studentRepository;
    private final TeacherRepository teacherRepository;

    public CourseServiceImpl(CourseRepository courseRepository, StudentsRepository studentRepository, TeacherRepository teacherRepository) {
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
        this.teacherRepository = teacherRepository;
    }

    @Override
    public List<Student> listStudentsByCourse(Long courseId) {
        Optional<Course> course = this.courseRepository.findById(courseId);
        if(course.isEmpty()) return new ArrayList<>();
        return course.get().getStudents();
    }

    @Override
    public Course addStudentInCourse(String username, Long courseId) {
        Optional<Student> studentFromDb = this.studentRepository.findById(username);
        Optional<Course> courseFromDb = this.courseRepository.findById(courseId);

        if(studentFromDb.isEmpty() || courseFromDb.isEmpty()) return null;

        courseFromDb.get().getStudents().add(studentFromDb.get());

        return this.courseRepository.save(courseFromDb.get());
    }

    @Override
    public List<Course> listAllCourses() {
        return this.courseRepository.findAll();
    }

    @Override
    public Course findById(Long courseId) {
        return this.courseRepository.findById(courseId).orElse(null);
    }

    @Override
    public void saveCourse(String courseName, String courseDescription, Long teacherId) {
        List<Course> coursesList= this.listAllCourses();
        if(coursesList.stream().filter(courseFromDB->courseFromDB.getName().equals(courseName)).findFirst().orElse(null)!=null) return; //nema 2 kursa so isto ime
        Optional<Teacher> teacher= teacherRepository.findById(teacherId);
        Course newCourse= new Course(courseName, courseDescription, new ArrayList<>(), teacher.get());
        this.courseRepository.save(newCourse);
    }

    @Override
    public void deleteCourse(Long courseId) {
        Course course= this.findById(courseId);

        this.courseRepository.delete(course);
    }

    @Override
    public void editCourse(Long courseId, String courseName, String courseDescription, Long teacherId) {
        Course course= this.findById(courseId);
        Teacher teacher=this.teacherRepository.findById(teacherId).orElse(null);
        course.setTeacher(teacher);
        course.setName(courseName);
        course.setDescription(courseDescription);
        this.courseRepository.save(course);
    }
}
