package mk.ukim.finki.wp.lab.web.controller;

import lombok.extern.slf4j.Slf4j;
import mk.ukim.finki.wp.lab.model.Course;
import mk.ukim.finki.wp.lab.model.Teacher;
import mk.ukim.finki.wp.lab.service.CourseService;
import mk.ukim.finki.wp.lab.service.TeacherService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Comparator;
import java.util.List;

@Controller
@RequestMapping("/courses")
@Slf4j
public class CourseController {
    private final CourseService courseService;
    private final TeacherService teacherService;

    public CourseController(CourseService courseService, TeacherService teacherService) {
        this.courseService = courseService;
        this.teacherService = teacherService;
    }

    @GetMapping
    public String getCoursesPage(@RequestParam(required = false) String error, Model model){
        List<Course> courseList = this.courseService.listAllCourses();
        courseList.sort(Comparator.comparing(Course::getName)); //sortirame po azbucen red spored imeto
        model.addAttribute("courseList", courseList);
        return "listCourses";
    }

    @PostMapping
    public String postCoursesPage(@RequestParam(required = false) Long courseId, @RequestParam(required = false) String action, Model model, HttpSession session){
        if(action != null) {
            log.info("AVION MORAT DA E: " + action);
            // edit-582
            // ["edit", "582"]
            String splitAction = action.split("-")[0];
            String splitCourseId = action.split("-")[1];

            switch (splitAction) {
                case "edit":
                    return "redirect:/courses/edit-form/" + splitCourseId;
                case "delete":
                    return "";
            }
        }
        session.setAttribute("courseId", courseId);

        return "redirect:/AddStudent";
    }
    @PostMapping("/add")
    public String saveCourse(@RequestParam String courseName, @RequestParam String courseDescription, @RequestParam Long teacherId){
        this.courseService.saveCourse(courseName,courseDescription,teacherId);
        return "redirect:/courses";
    }

    @PostMapping("/edit")
    public String editCourse(@RequestParam Long courseId, @RequestParam String courseName, @RequestParam String courseDescription, @RequestParam Long teacherId){
        this.courseService.editCourse(courseId, courseName, courseDescription,teacherId);
        return "redirect:/courses";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteCourse(@PathVariable Long id){
        this.courseService.deleteCourse(id);
        return "redirect:/courses";
    }

    @GetMapping("/add-form")
    public String getAddCourse(Model model){
        List<Teacher> teachers=this.teacherService.findAll();
        model.addAttribute("teachers", teachers);
        model.addAttribute("type", "add");
        return "add-course";
    }

    @GetMapping("/edit-form/{id}")
    public String getEditCourse(@PathVariable Long id, Model model){
        Course course = this.courseService.findById(id);
        List<Teacher> teachers=this.teacherService.findAll();
        model.addAttribute("teachers", teachers);
        model.addAttribute("type", "edit");
        model.addAttribute("courseId", course.getCourseId());
        model.addAttribute("courseName", course.getName());
        model.addAttribute("courseDescription", course.getDescription());
        model.addAttribute("teacherId", course.getTeacher().getId());
        return "add-course";
    }
}
