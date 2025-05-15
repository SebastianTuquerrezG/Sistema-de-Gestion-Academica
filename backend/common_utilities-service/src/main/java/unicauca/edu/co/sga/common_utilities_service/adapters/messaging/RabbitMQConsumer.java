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

@Service
@RequiredArgsConstructor
@Slf4j
public class RabbitMQConsumer {
    private final EnrollService enrollService;
    private final EvaluationService evaluationService;

    @RabbitListener(queues = RabbitMQConfig.QUEUE_ENROLL)
    public void readEnrollData(@Payload EnrollRequestDTO message) {
        EnrollResponseDTO enrollResponseDTO = enrollService.saveEnroll(message);
        log.info("Message from EVALUATION microservice (ENROLL ENTITY): {}", message);
        if(enrollResponseDTO != null){
            log.info("Database updated with new enroll data. Data {}", enrollResponseDTO);
        }else{
            log.info("There are some errors with the database (ENROLL).");
        }
    }

    @RabbitListener(queues = RabbitMQConfig.QUEUE_EVALUATION)
    public void readEvaluationData(@Payload EvaluationRequestDTO message){
        EvaluationResponseDTO evaluationResponseDTO = evaluationService.saveEvaluation(message);
        log.info("Message from EVALUATION microservice (EVALUATION ENTITY): {}", message);
        if(evaluationResponseDTO != null){
            log.info("Database updated with new evaluation data. Data {}", evaluationResponseDTO);
        }else{
            log.info("There are some errors with the database (EVALUATION).");
        }
    }
}
