package mk.ukim.finki.wp.lab.repository.in_memory;

import mk.ukim.finki.wp.lab.model.Teacher;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Repository
public class InMemoryTeacherRepository {
    private final static List<Teacher> teacherList= new ArrayList<>();

    @PostConstruct
    void init(){
        teacherList.add(new Teacher("newTeacher1", "newTeacharoski1"));
        teacherList.add(new Teacher("newTeacher2", "newTeacharoski2"));
        teacherList.add(new Teacher("newTeacher3", "newTeacharoski3"));
        teacherList.add(new Teacher("newTeacher4", "newTeacharoski4"));
        teacherList.add(new Teacher("newTeacher5", "newTeacharoski5"));
    }

    public List<Teacher> findAll(){

        return teacherList;
    }

    public Teacher findById(Long teacherId){
        for (Teacher teacher : teacherList) {
            if (teacher.getId().equals(teacherId)) {
                return teacher;
            }
        }
        return null;
    }
}
