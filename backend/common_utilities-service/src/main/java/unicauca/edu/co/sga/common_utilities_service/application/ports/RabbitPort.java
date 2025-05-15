package unicauca.edu.co.sga.common_utilities_service.application.ports;

import unicauca.edu.co.sga.common_utilities_service.application.dto.request.*;

public interface RabbitPort {
    void sendStudent(StudentRequestDTO student);
    void sendTeacher(TeacherRequestDTO teacher);
    void sendCourse(CourseRequestDTO course);
    void sendRA(RARequestDTO ra);
    void sendSubject(SubjectRequestDTO subject);
}
