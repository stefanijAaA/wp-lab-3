package mk.ukim.finki.wp.lab.service.impl;

import mk.ukim.finki.wp.lab.model.Course;
import mk.ukim.finki.wp.lab.model.Student;
import mk.ukim.finki.wp.lab.model.Teacher;
import mk.ukim.finki.wp.lab.repository.CourseRepository;
import mk.ukim.finki.wp.lab.repository.StudentRepository;
import mk.ukim.finki.wp.lab.repository.TeacherRepository;
import mk.ukim.finki.wp.lab.service.CourseService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {
    private final CourseRepository courseRepository; //SpringBoot injects this repository into CourseService(dependencyInjection by constructor)
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;

    public CourseServiceImpl(CourseRepository courseRepository, StudentRepository studentRepository, TeacherRepository teacherRepository) {
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
        this.teacherRepository = teacherRepository;
    }

    @Override
    public List<Student> listStudentsByCourse(Long courseId) {
        return this.courseRepository.findAllStudentsByCourse(courseId);
    }

    @Override
    public Course addStudentInCourse(String username, Long courseId) {
        Student studentFromDb = this.studentRepository.findByUsername(username);
        Course courseFromDb = this.courseRepository.findById(courseId);

        if(studentFromDb == null || courseFromDb == null) return null;

        return this.courseRepository.addStudentToCourse(studentFromDb, courseFromDb);
    }

    @Override
    public List<Course> listAllCourses() {
        return this.courseRepository.listAllCourses();
    }

    @Override
    public Course findById(Long courseId) {
        return this.courseRepository.findById(courseId);
    }

    @Override
    public void saveCourse(String courseName, String courseDescription, Long teacherId) {
        List<Course> coursesList= this.listAllCourses();
        if(coursesList.stream().filter(courseFromDB->courseFromDB.getName().equals(courseName)).findFirst().orElse(null)!=null) return; //nema 2 kursa so isto ime
        Teacher teacher= teacherRepository.findById(teacherId);
        Course newCourse= new Course(courseName, courseDescription, new ArrayList<>(), teacher);
        this.courseRepository.saveCourse(newCourse);
    }

    @Override
    public void deleteCourse(Long courseId) {
        Course course= this.findById(courseId);

        this.courseRepository.deleteCourse(course);
    }

    @Override
    public void editCourse(Long courseId, String courseName, String courseDescription, Long teacherId) {
        Course course= this.findById(courseId);
        Teacher teacher=this.teacherRepository.findById(teacherId);
        this.courseRepository.editCourse(course,teacher, courseName, courseDescription);
    }
}
