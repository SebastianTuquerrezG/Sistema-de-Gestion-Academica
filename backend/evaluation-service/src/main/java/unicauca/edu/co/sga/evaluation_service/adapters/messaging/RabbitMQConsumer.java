package unicauca.edu.co.sga.evaluation_service.adapters.messaging;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import unicauca.edu.co.sga.evaluation_service.application.dto.request.*;
import unicauca.edu.co.sga.evaluation_service.application.dto.response.*;
import unicauca.edu.co.sga.evaluation_service.application.services.*;
import unicauca.edu.co.sga.evaluation_service.infrastructure.config.RabbitMQConfig;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.entities.*;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.repositories.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class RabbitMQConsumer {

    private final CourseService courseService;
    private final TeacherService teacherService;
    private final SubjectService subjectService;
    private final StudentService studentService;
    private final CourseRepository courseRepository;
    private final TeacherRepository teacherRepository;
    private final SubjectRepository subjectRepository;
    private final StudentRepository studentRepository;

    // Methods for consumer from Common_utilities microservice.
    @RabbitListener(queues = RabbitMQConfig.QUEUE_COURSE)
    public void readCourseData(@Payload CourseRequestDTO message) {
        CourseResponseDTO courseResponseDTO = courseService.saveCourse(message);
        log.info("Message from COURSE microservice (COURSE ENTITY): {}", message);
        if(courseResponseDTO != null){
            log.info("Database updated with new COURSE data. Data {}", courseResponseDTO);
        }else{
            log.info("There are some errors with the database (COURSE).");
        }
    }

    @RabbitListener(queues = RabbitMQConfig.QUEUE_UPDATE_COURSE)
    public void readCourseData(@Payload CourseEntity message) {
        courseRepository.save(message);
        log.info("Message from COURSE microservice (COURSE ENTITY UPDATED): {}", message);
        log.info("Database updated with updated COURSE data. Data {}", message);
    }

    @RabbitListener(queues = RabbitMQConfig.QUEUE_DELETE_COURSE)
    public void deleteCourseData(@Payload CourseEntity message) {
        courseRepository.deleteById(message.getId());
        log.info("Message from COURSE microservice (COURSE ENTITY DELETED): {}", message);
        log.info("Database updated with deleted COURSE data. Data {}", message);
    }

    @RabbitListener(queues = RabbitMQConfig.QUEUE_TEACHER)
    public void readTeacherData(@Payload TeacherRequestDTO message) {
        TeacherResponseDTO teacherResponseDTO = teacherService.saveTeacher(message);
        log.info("Message from TEACHER microservice (TEACHER ENTITY): {}", message);
        if(teacherResponseDTO != null){
            log.info("Database updated with new TEACHER data. Data {}", teacherResponseDTO);
        }else{
            log.info("There are some errors with the database (TEACHER).");
        }
    }

    @RabbitListener(queues = RabbitMQConfig.QUEUE_UPDATE_TEACHER)
    public void readTeacherData(@Payload TeacherEntity message) {
        teacherRepository.save(message);
        log.info("Message from TEACHER microservice (TEACHER ENTITY UPDATED): {}", message);
        log.info("Database updated with updated TEACHER data. Data {}", message);
    }

    @RabbitListener(queues = RabbitMQConfig.QUEUE_DELETE_TEACHER)
    public void deleteTeacherData(@Payload TeacherEntity message) {
        courseRepository.deleteById(message.getId());
        log.info("Message from TEACHER microservice (TEACHER ENTITY DELETED): {}", message);
        log.info("Database updated with deleted TEACHER data. Data {}", message);
    }

    @RabbitListener(queues = RabbitMQConfig.QUEUE_STUDENT)
    public void readStudentData(@Payload StudentRequestDTO message) {
        StudentResponseDTO studentResponseDTO = studentService.saveStudent(message);
        log.info("Message from STUDENT microservice (STUDENT ENTITY): {}", message);
        if(studentResponseDTO != null){
            log.info("Database updated with new STUDENT data. Data {}", studentResponseDTO);
        }else{
            log.info("There are some errors with the database (STUDENT).");
        }
    }

    @RabbitListener(queues = RabbitMQConfig.QUEUE_UPDATE_STUDENT)
    public void readStudentData(@Payload StudentEntity message) {
        studentRepository.save(message);
        log.info("Message from STUDENT microservice (STUDENT ENTITY UPDATED): {}", message);
        log.info("Database updated with updated STUDENT data. Data {}", message);
    }

    @RabbitListener(queues = RabbitMQConfig.QUEUE_DELETE_STUDENT)
    public void deleteStudentData(@Payload StudentEntity message) {
        studentRepository.deleteById(message.getId());
        log.info("Message from STUDENT microservice (STUDENT ENTITY DELETED): {}", message);
        log.info("Database updated with deleted STUDENT data. Data {}", message);
    }

    @RabbitListener(queues = RabbitMQConfig.QUEUE_SUBJECT)
    public void readSubjectData(@Payload SubjectRequestDTO message) {
        SubjectResponseDTO subjectResponseDTO = subjectService.saveSubject(message);
        log.info("Message from SUBJECT microservice (SUBJECT ENTITY): {}", message);
        if(subjectResponseDTO != null){
            log.info("Database updated with new SUBJECT data. Data {}", subjectResponseDTO);
        }else{
            log.info("There are some errors with the database (SUBJECT).");
        }
    }

    @RabbitListener(queues = RabbitMQConfig.QUEUE_UPDATE_SUBJECT)
    public void readSubjectData(@Payload SubjectEntity message) {
        subjectRepository.save(message);
        log.info("Message from SUBJECT microservice (SUBJECT ENTITY UPDATED): {}", message);
        log.info("Database updated with updated SUBJECT data. Data {}", message);
    }

    @RabbitListener(queues = RabbitMQConfig.QUEUE_DELETE_SUBJECT)
    public void deleteSubjectData(@Payload SubjectEntity message) {
        subjectRepository.deleteById(message.getId());
        log.info("Message from SUBJECT microservice (SUBJECT ENTITY DELETED): {}", message);
        log.info("Database updated with deleted SUBJECT data. Data {}", message);
    }
}
