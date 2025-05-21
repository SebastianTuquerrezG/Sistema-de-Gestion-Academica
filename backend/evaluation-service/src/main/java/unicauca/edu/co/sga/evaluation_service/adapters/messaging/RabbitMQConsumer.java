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

@Service
@RequiredArgsConstructor
@Slf4j
public class RabbitMQConsumer {

    private final CourseService courseService;
    private final TeacherService teacherService;
    private final SubjectService subjectService;
    private final StudentService studentService;

    // TODO: Create all the methods to read the message from Rubric

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

    // TODO: Create the service for RA component.
//    @RabbitListener(queues = RabbitMQConfig.QUEUE_RA)
//    public void readRAData(@Payload RARequestDTO message) {
//        RAResponseDTO raResponseDTO = raServive.saveRA(message);
//        log.info("Message from RA microservice (RA ENTITY): {}", message);
//        if(raResponseDTO != null){
//            log.info("Database updated with new RA data. Data {}", raResponseDTO);
//        }else{
//            log.info("There are some errors with the database (RA).");
//        }
//    }
}
