package mk.ukim.finki.wp.lab.web;

import lombok.extern.slf4j.Slf4j;
import mk.ukim.finki.wp.lab.model.Course;
import mk.ukim.finki.wp.lab.service.CourseService;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "CoursesListServlet ", urlPatterns = "/listCourses") //mapiranje
@Slf4j
public class CoursesListServlet extends HttpServlet {

    private final CourseService courseService;
    private final SpringTemplateEngine springTemplateEngine;


    public CoursesListServlet(CourseService courseService, SpringTemplateEngine springTemplateEngine) {
        this.courseService = courseService;
        this.springTemplateEngine = springTemplateEngine;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        WebContext webContext = new WebContext(request, response, request.getServletContext());
        List<Course> courseList = this.courseService.listAllCourses();
        webContext.setVariable("courseList", courseList);
        this.springTemplateEngine.process("listCourses.html", webContext, response.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getSession().setAttribute("courseId", req.getParameter("courseId"));

        resp.sendRedirect("/AddStudent");
    }
}
