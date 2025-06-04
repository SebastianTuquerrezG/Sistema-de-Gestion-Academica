package unicauca.edu.co.sga.common_utilities_service.adapters.messaging;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import unicauca.edu.co.sga.common_utilities_service.application.dto.request.EnrollRequestDTO;
import unicauca.edu.co.sga.common_utilities_service.application.dto.request.EvaluationRequestDTO;
import unicauca.edu.co.sga.common_utilities_service.application.dto.response.EnrollResponseDTO;
import unicauca.edu.co.sga.common_utilities_service.application.dto.response.EvaluationResponseDTO;
import unicauca.edu.co.sga.common_utilities_service.application.services.EnrollService;
import unicauca.edu.co.sga.common_utilities_service.application.services.EvaluationService;
import unicauca.edu.co.sga.common_utilities_service.infrastructure.config.RabbitMQConfig;
import unicauca.edu.co.sga.common_utilities_service.infrastructure.persistence.entities.EnrollEntity;
import unicauca.edu.co.sga.common_utilities_service.infrastructure.persistence.entities.EvaluationEntity;
import unicauca.edu.co.sga.common_utilities_service.infrastructure.persistence.repositories.EnrollRepository;
import unicauca.edu.co.sga.common_utilities_service.infrastructure.persistence.repositories.EvaluationRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class RabbitMQConsumer {
    private final EnrollService enrollService;
    private final EvaluationService evaluationService;
    private final EvaluationRepository evaluationRepository;
    private final EnrollRepository enrollRepository;

    @RabbitListener(queues = RabbitMQConfig.QUEUE_ENROLL)
    public void readEnrollData(@Payload EnrollRequestDTO message) {
        EnrollResponseDTO enrollResponseDTO = enrollService.saveEnroll(message);
        log.info("Message from EVALUATION microservice (ENROLL ENTITY CREATED): {}", message);
        if(enrollResponseDTO != null){
            log.info("Database updated with new enroll data. Data {}", enrollResponseDTO);
        }else{
            log.info("There are some errors with the database (ENROLL).");
        }
    }

    @RabbitListener(queues = RabbitMQConfig.QUEUE_UPDATE_ENROLL)
    public void readEnrollData(@Payload EnrollEntity message) {
        enrollRepository.save(message);
        log.info("Message from EVALUATION microservice (ENROLL ENTITY UPDATED): {}", message);
        log.info("Database updated with updated enroll data. Data {}", message);
    }

    @RabbitListener(queues = RabbitMQConfig.QUEUE_DELETE_ENROLL)
    public void deleteEnrollData(@Payload EnrollEntity message) {
        enrollRepository.deleteById(message.getId());
        log.info("Message from EVALUATION microservice (ENROLL ENTITY DELETED): {}", message);
        log.info("Database updated with deleted enroll data. Data {}", message);
    }

    @RabbitListener(queues = RabbitMQConfig.QUEUE_EVALUATION)
    public void readEvaluationData(@Payload EvaluationRequestDTO message){
        EvaluationResponseDTO evaluationResponseDTO = evaluationService.saveEvaluation(message);
        log.info("Message from EVALUATION microservice (EVALUATION ENTITY CREATED): {}", message);
        if(evaluationResponseDTO != null){
            log.info("Database updated with new evaluation data. Data {}", evaluationResponseDTO);
        }else{
            log.info("There are some errors with the database (EVALUATION).");
        }
    }

    @RabbitListener(queues = RabbitMQConfig.QUEUE_UPDATE_EVALUATION)
    public void readEvaluationData(@Payload EvaluationEntity message){
        evaluationRepository.save(message);
        log.info("Message from EVALUATION microservice (EVALUATION ENTITY UPDATED): {}", message);
        log.info("Database updated with updated evaluation data. Data {}", message);
    }

    @RabbitListener(queues = RabbitMQConfig.QUEUE_DELETE_EVALUATION)
    public void deleteEvaluationData(@Payload EvaluationEntity message){
        evaluationRepository.deleteById(message.getId());
        log.info("Message from EVALUATION microservice (EVALUATION ENTITY DELETED): {}", message);
        log.info("Database updated with deleted evaluation data. Data {}", message);
    }
}
