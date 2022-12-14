package mk.ukim.finki.wp.lab.web;

import mk.ukim.finki.wp.lab.model.Student;
import mk.ukim.finki.wp.lab.service.CourseService;
import mk.ukim.finki.wp.lab.service.StudentService;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "ListStudentServlet", urlPatterns = "/AddStudent")
public class ListStudentServlet extends HttpServlet {
    private final StudentService studentService;
    private final SpringTemplateEngine springTemplateEngine;
    private final CourseService courseService;

    public ListStudentServlet(StudentService studentService, SpringTemplateEngine springTemplateEngine, CourseService courseService) {
        this.studentService = studentService;
        this.springTemplateEngine = springTemplateEngine;
        this.courseService = courseService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        WebContext webContext = new WebContext(req, resp, req.getServletContext());
        Long courseId = Long.parseLong(String.valueOf(req.getSession().getAttribute("courseId")));
        webContext.setVariable("courseId", courseId);
        List<Student> studentList = this.studentService.listAll();
        List<Student> filterList= this.courseService.listStudentsByCourse(courseId);
        List<Student> finalFiltered= this.studentService.getFilteredStudents(filterList);
        webContext.setVariable("studentList", finalFiltered);


        this.springTemplateEngine.process("listStudents.html", webContext, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String studentUsername = req.getParameter("studentUsername");
        Long courseId = Long.parseLong(String.valueOf(req.getSession().getAttribute("courseId")));


        this.courseService.addStudentInCourse(studentUsername, courseId);

        resp.sendRedirect("/StudentEnrollmentSummary");
    }
}
