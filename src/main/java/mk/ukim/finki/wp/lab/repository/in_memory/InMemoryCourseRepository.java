package mk.ukim.finki.wp.lab.repository.in_memory;

import mk.ukim.finki.wp.lab.model.Course;
import mk.ukim.finki.wp.lab.model.Student;
import mk.ukim.finki.wp.lab.model.Teacher;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Repository
public class InMemoryCourseRepository {
    public static final List<Course> courseList = new ArrayList<>();

    @PostConstruct
    public void init() {
        courseList.add(new Course( "course1", "this is course1", new ArrayList<>(), new Teacher("teacher1", "teacharoski")));
        courseList.add(new Course("course2", "this is course2", new ArrayList<>(),new Teacher("teacher2", "teacharoski")));
        courseList.add(new Course("course3", "this is course3", new ArrayList<>(), new Teacher("teacher3", "teacharoski")));
        courseList.add(new Course("course4", "this is course4", new ArrayList<>(), new Teacher("teacher4", "teacharoski")));
        courseList.add(new Course("course5", "this is course5", new ArrayList<>(), new Teacher("teacher5", "teacharoski")));
    }

    public List<Course> findAllCourses() {
        return courseList;
    }

    public Course findById(Long courseId) {
        for (Course course : courseList) {
            if (course.getCourseId().equals(courseId)) {
                return course;
            }
        }
        return null;
    }

    public List<Student> findAllStudentsByCourse(Long courseId) {
        return this.findById(courseId).getStudents();
    }

    public Course addStudentToCourse(Student student, Course course) {
        Student studentExists = course.getStudents().stream().filter(courseStudent -> courseStudent.getUsername().equals(student.getUsername())).findFirst().orElse(null);

        if (studentExists == null)
            course.getStudents().add(student);

        return course;
    }

    public List<Course> listAllCourses() {

        return courseList;
    }

    public void saveCourse(Course course){
        courseList.add(course);
    }

    public void deleteCourse(Course course){
        courseList.remove(course);
    }

    public void editCourse(Course course, Teacher teacher, String courseName, String courseDescription){
        course.setTeacher(teacher);
        course.setName(courseName);
        course.setDescription(courseDescription);
    }
}
