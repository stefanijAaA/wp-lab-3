package mk.ukim.finki.wp.lab.web;

import mk.ukim.finki.wp.lab.service.StudentService;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "CreateStudentServlet", urlPatterns = "/CreateStudent")
public class CreateStudentServlet extends HttpServlet {

    private final SpringTemplateEngine springTemplateEngine;
    private final StudentService studentService;

    public CreateStudentServlet(SpringTemplateEngine springTemplateEngine, StudentService studentService) {
        this.springTemplateEngine = springTemplateEngine;
        this.studentService = studentService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        WebContext webContext = new WebContext(req, resp, req.getServletContext());

        this.springTemplateEngine.process("addStudent.html", webContext, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String studentUsername = req.getParameter("username");
        String studentPassword = req.getParameter("password");
        String studentName = req.getParameter("name");
        String studentSurname = req.getParameter("surname");

        this.studentService.save(studentUsername, studentPassword, studentName, studentSurname);

        resp.sendRedirect("/AddStudent");
    }
}
