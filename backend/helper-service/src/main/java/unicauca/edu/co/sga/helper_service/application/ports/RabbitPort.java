package unicauca.edu.co.sga.helper_service.application.ports;

import unicauca.edu.co.sga.helper_service.application.dto.request.*;
import unicauca.edu.co.sga.helper_service.infrastructure.persistence.entities.StudentEntity;
import unicauca.edu.co.sga.helper_service.infrastructure.persistence.entities.TeacherEntity;

public interface RabbitPort {
    void sendStudent(StudentRequestDTO student);
    void sendTeacher(TeacherRequestDTO teacher);
    void sendCourse(CourseRequestDTO course);
    void sendRA(RARequestDTO ra);
    void sendSubject(SubjectRequestDTO subject);
}
