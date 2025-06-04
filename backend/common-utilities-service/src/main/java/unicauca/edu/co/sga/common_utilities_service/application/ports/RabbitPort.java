package unicauca.edu.co.sga.common_utilities_service.application.ports;

import unicauca.edu.co.sga.common_utilities_service.application.dto.request.*;
import unicauca.edu.co.sga.common_utilities_service.infrastructure.persistence.entities.CourseEntity;
import unicauca.edu.co.sga.common_utilities_service.infrastructure.persistence.entities.StudentEntity;
import unicauca.edu.co.sga.common_utilities_service.infrastructure.persistence.entities.SubjectEntity;
import unicauca.edu.co.sga.common_utilities_service.infrastructure.persistence.entities.TeacherEntity;

public interface RabbitPort {
    void sendStudent(StudentRequestDTO student);
    void updateStudent(StudentEntity student);
    void deleteStudent(StudentEntity student);
    void sendTeacher(TeacherRequestDTO teacher);
    void updateTeacher(TeacherEntity teacher);
    void deleteTeacher(TeacherEntity teacher);
    void sendCourse(CourseRequestDTO course);
    void updateCourse(CourseEntity course);
    void deleteCourse(CourseEntity course);
    void sendRA(RARequestDTO ra);
    void sendSubject(SubjectRequestDTO subject);
    void updateSubject(SubjectEntity subject);
    void deleteSubject(SubjectEntity subject);
}
