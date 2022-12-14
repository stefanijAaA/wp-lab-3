package mk.ukim.finki.wp.lab.web;

import lombok.extern.slf4j.Slf4j;
import mk.ukim.finki.wp.lab.model.Course;
import mk.ukim.finki.wp.lab.model.Grade;
import mk.ukim.finki.wp.lab.repository.jpa.GradeRepository;
import mk.ukim.finki.wp.lab.service.CourseService;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "StudentEnrollmentSummary", urlPatterns = "/StudentEnrollmentSummaryBAK")
@Slf4j
public class StudentEnrollmentSummary extends HttpServlet {

    private final SpringTemplateEngine springTemplateEngine;
    private final CourseService courseService;
    private final GradeRepository gradeRepository;

    public StudentEnrollmentSummary(SpringTemplateEngine springTemplateEngine, CourseService courseService, GradeRepository gradeRepository) {
        this.springTemplateEngine = springTemplateEngine;
        this.courseService = courseService;
        this.gradeRepository = gradeRepository;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        WebContext webContext = new WebContext(req, resp, req.getServletContext());

        Long courseId = Long.parseLong(String.valueOf(req.getSession().getAttribute("courseId")));
        Course course = this.courseService.findById(courseId);

        log.info("COURSE NO STUDENTS: " + course.getStudents().size());
        List<Grade> grades = new ArrayList<>();
        webContext.setVariable("courseName", course.getName());
        webContext.setVariable("studentList", course.getStudents());
        course.getStudents().forEach(student -> {
            Grade grade = this.gradeRepository.findByStudentAndCourse(student.getUsername(), courseId);

            grades.add(grade);
        });
        webContext.setVariable("grades", grades);

        this.springTemplateEngine.process("/studentsInCourse.html", webContext, resp.getWriter());
    }
}
