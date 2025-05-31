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

    private final RubricService rubricService;
    private final CriteriaService criteriaService;
    private final PerformanceLevelService performanceLevelService;

    // Methods to read the message from Rubric
    @RabbitListener(queues = RabbitMQConfig.QUEUE_RUBRIC)
    public void readRubricData(@Payload RubricRequestDTO message) {
        RubricEntity rubricResponseDTO = rubricService.saveRubric(message);
        log.info("Message from RUBRIC microservice (RUBRIC ENTITY): {}", message);
        if(rubricResponseDTO != null){
            log.info("Database updated with new RUBRIC data. Data {}", rubricResponseDTO);
        }else{
            log.info("There are some errors with the database (RUBRIC).");
        }
    }

    @RabbitListener(queues = RabbitMQConfig.QUEUE_UPDATE_RUBRIC)
    public void updateRubricData(@Payload RubricRequestDTO message) {
        RubricEntity rubricResponseDTO = rubricService.updateRubricRabbit(message);
        log.info("Message from RUBRIC microservice (RUBRIC ENTITY UPDATED): {}", message);
        if(rubricResponseDTO != null){
            log.info("Database updated with updated RUBRIC data. Data {}", rubricResponseDTO);
        }else{
            log.info("There are some errors with the database (RUBRIC UPDATED).");
        }
    }

    @RabbitListener(queues = RabbitMQConfig.QUEUE_DELETE_RUBRIC)
    public void deleteRubricData(@Payload RubricRequestDTO message) {
        boolean result = rubricService.deleteRubric(message.getIdRubrica());
        log.info("Message from RUBRIC microservice (RUBRIC ENTITY DELETED): {}", message);
        if(result){
            log.info("Database deleted with updated RUBRIC data.");
        }else{
            log.info("There are some errors with the database (RUBRIC DELETED).");
        }
    }

    @RabbitListener(queues = RabbitMQConfig.QUEUE_CRITERIA)
    public void readCriteriaData(@Payload CriteriaRequestDTO message) {
        CriteriaEntity criteriaEntity = criteriaService.saveCriteria(message);
        log.info("Message from RUBRIC microservice (CRITERIA ENTITY): {}", message);
        if(criteriaEntity != null){
            log.info("Database updated with new CRITERIA data. Data {}", criteriaEntity);
        }else{
            log.info("There are some errors with the database (CRITERIA).");
        }
    }

    @RabbitListener(queues = RabbitMQConfig.QUEUE_UPDATE_CRITERIA)
    public void updateCriteriaData(@Payload CriteriaRequestDTO message) {
        CriteriaEntity criteriaEntity = criteriaService.saveCriteria(message);
        log.info("Message from RUBRIC microservice (CRITERIA ENTITY UPDATED): {}", message);
        if(criteriaEntity != null){
            log.info("Database updated with updated CRITERIA data. Data {}", criteriaEntity);
        }else{
            log.info("There are some errors with the database (CRITERIA UPDATED).");
        }
    }

    @RabbitListener(queues = RabbitMQConfig.QUEUE_DELETE_CRITERIA)
    public void deleteCriteriaData(@Payload CriteriaRequestDTO message) {
        boolean criteriaEntity = criteriaService.deleteCriteria(message.getIdCriterio());
        log.info("Message from RUBRIC microservice (CRITERIA ENTITY DELETED): {}", message);
        if(criteriaEntity){
            log.info("Database updated with deleted CRITERIA data.");
        }else{
            log.info("There are some errors with the database (CRITERIA DELETED).");
        }
    }

    @RabbitListener(queues = RabbitMQConfig.QUEUE_PERFORMANCE)
    public void readPerformanceData(@Payload PerformanceLevelRequestDTO message) {
        PerformanceEntity entity = performanceLevelService.savePerformanceLevel(message);
        log.info("Message from RUBRIC microservice (PERFORMANCE ENTITY): {}", message);
        if(entity != null){
            log.info("Database updated with new PERFORMANCE data. Data {}", entity);
        }else{
            log.info("There are some errors with the database (PERFORMANCE).");
        }
    }

    @RabbitListener(queues = RabbitMQConfig.QUEUE_UPDATE_PERFORMANCE)
    public void updatePerformanceData(@Payload PerformanceLevelRequestDTO message) {
        PerformanceEntity entity = performanceLevelService.savePerformanceLevel(message);
        log.info("Message from RUBRIC microservice (PERFORMANCE ENTITY UPDATED): {}", message);
        if(entity != null){
            log.info("Database updated with UPDATED PERFORMANCE data. Data {}", entity);
        }else{
            log.info("There are some errors with the database (PERFORMANCE UPDATED).");
        }
    }

    @RabbitListener(queues = RabbitMQConfig.QUEUE_DELETE_PERFORMANCE)
    public void deletePerformanceData(@Payload PerformanceLevelRequestDTO message) {
        boolean entity = performanceLevelService.deletePerformanceLevel(message.getIdNivel());
        log.info("Message from RUBRIC microservice (PERFORMANCE ENTITY DELETED): {}", message);
        if(entity){
            log.info("Database updated with DELETED PERFORMANCE data.");
        }else{
            log.info("There are some errors with the database (PERFORMANCE DELETED).");
        }
    }



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
