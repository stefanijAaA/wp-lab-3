package mk.ukim.finki.wp.lab.service;

import mk.ukim.finki.wp.lab.model.Course;
import mk.ukim.finki.wp.lab.model.Student;

import java.util.List;

public interface CourseService{
    public List<Student> listStudentsByCourse(Long courseId);
    public Course addStudentInCourse(String username, Long courseId);

    public List<Course> listAllCourses();

    public Course findById(Long courseId);

    void saveCourse(String courseName, String courseDescription, Long teacherId);

    void deleteCourse(Long courseId);

    void editCourse(Long courseId,String courseName, String courseDescription, Long teacherId);

}