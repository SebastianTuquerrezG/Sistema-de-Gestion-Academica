package unicauca.edu.co.sga.common_utilities_service.application.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import unicauca.edu.co.sga.common_utilities_service.adapters.messaging.RabbitMQProducer;
import unicauca.edu.co.sga.common_utilities_service.application.dto.request.*;
import unicauca.edu.co.sga.common_utilities_service.application.ports.RabbitPort;
import unicauca.edu.co.sga.common_utilities_service.infrastructure.config.RabbitMQConfig;
import unicauca.edu.co.sga.common_utilities_service.infrastructure.persistence.entities.CourseEntity;
import unicauca.edu.co.sga.common_utilities_service.infrastructure.persistence.entities.StudentEntity;
import unicauca.edu.co.sga.common_utilities_service.infrastructure.persistence.entities.SubjectEntity;
import unicauca.edu.co.sga.common_utilities_service.infrastructure.persistence.entities.TeacherEntity;

@Service
@Transactional
@RequiredArgsConstructor
public class RabbitService implements RabbitPort {
    private final RabbitMQProducer rabbit;

    // TODO: With those methods we need to use it into each service when they do a CRUD.
    // EXAMPLE: Teacher create a new tuple, then send with rabbit from TeacherService, the entity.

    @Override
    public void sendStudent(StudentRequestDTO student) {
        rabbit.sendMessage(RabbitMQConfig.EXCHANGE, RabbitMQConfig.ROUTING_KEY_STUDENT, student);
    }

    @Override
    public void updateStudent(StudentEntity student) {
        rabbit.sendMessage(RabbitMQConfig.EXCHANGE, RabbitMQConfig.ROUTING_KEY_UPDATE_STUDENT, student);
    }

    @Override
    public void deleteStudent(StudentEntity student) {
        rabbit.sendMessage(RabbitMQConfig.EXCHANGE, RabbitMQConfig.ROUTING_KEY_DELETE_STUDENT, student);
    }

    @Override
    public void sendTeacher(TeacherRequestDTO teacher) {
        rabbit.sendMessage(RabbitMQConfig.EXCHANGE, RabbitMQConfig.ROUTING_KEY_TEACHER, teacher);
    }

    @Override
    public void updateTeacher(TeacherEntity teacher) {
        rabbit.sendMessage(RabbitMQConfig.EXCHANGE, RabbitMQConfig.ROUTING_KEY_UPDATE_TEACHER, teacher);
    }

    @Override
    public void deleteTeacher(TeacherEntity teacher) {
        rabbit.sendMessage(RabbitMQConfig.EXCHANGE, RabbitMQConfig.ROUTING_KEY_DELETE_TEACHER, teacher);
    }

    @Override
    public void sendCourse(CourseRequestDTO course) {
        rabbit.sendMessage(RabbitMQConfig.EXCHANGE, RabbitMQConfig.ROUTING_KEY_COURSE, course);
    }

    @Override
    public void updateCourse(CourseEntity course) {
        rabbit.sendMessage(RabbitMQConfig.EXCHANGE, RabbitMQConfig.ROUTING_KEY_UPDATE_COURSE, course);
    }

    @Override
    public void deleteCourse(CourseEntity course) {
        rabbit.sendMessage(RabbitMQConfig.EXCHANGE, RabbitMQConfig.ROUTING_KEY_DELETE_COURSE, course);
    }

    @Override
    public void sendRA(RARequestDTO ra) {
        rabbit.sendMessage(RabbitMQConfig.EXCHANGE, RabbitMQConfig.ROUTING_KEY_RA, ra);
    }

    @Override
    public void sendSubject(SubjectRequestDTO subject) {
        rabbit.sendMessage(RabbitMQConfig.EXCHANGE, RabbitMQConfig.ROUTING_KEY_SUBJECT, subject);
    }

    @Override
    public void updateSubject(SubjectEntity subject) {
        rabbit.sendMessage(RabbitMQConfig.EXCHANGE, RabbitMQConfig.ROUTING_KEY_UPDATE_SUBJECT, subject);
    }

    @Override
    public void deleteSubject(SubjectEntity subject) {
        rabbit.sendMessage(RabbitMQConfig.EXCHANGE, RabbitMQConfig.ROUTING_KEY_DELETE_SUBJECT, subject);
    }
}
