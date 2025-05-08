package unicauca.edu.co.sga.helper_service.application.ports;

import unicauca.edu.co.sga.helper_service.infrastructure.persistence.entities.StudentEntity;
import unicauca.edu.co.sga.helper_service.infrastructure.persistence.entities.TeacherEntity;

public interface RabbitPort {
    void sendStudent(StudentEntity student);
    void sendTeacher(TeacherEntity teacher);
}
