package unicauca.edu.co.sga.helper_service.adapters.messaging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import unicauca.edu.co.sga.helper_service.application.dto.request.EnrollRequestDTO;
import unicauca.edu.co.sga.helper_service.application.dto.request.EvaluationRequestDTO;
import unicauca.edu.co.sga.helper_service.application.dto.response.EnrollResponseDTO;
import unicauca.edu.co.sga.helper_service.infrastructure.config.RabbitMQConfig;
import unicauca.edu.co.sga.helper_service.infrastructure.persistence.entities.EnrollEntity;
import unicauca.edu.co.sga.helper_service.infrastructure.persistence.entities.EvaluationEntity;
import unicauca.edu.co.sga.helper_service.infrastructure.persistence.mappers.EnrollMapper;
import unicauca.edu.co.sga.helper_service.infrastructure.persistence.mappers.EvaluationMapper;
import unicauca.edu.co.sga.helper_service.infrastructure.persistence.repositories.EnrollRepository;
import unicauca.edu.co.sga.helper_service.infrastructure.persistence.repositories.EvaluationRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class RabbitMQConsumer {
    private final EnrollMapper enrollMapper;
    private final EnrollRepository enrollRepository;
    private final EvaluationMapper evaluationMapper;
    private final EvaluationRepository evaluationRepository;

    @RabbitListener(queues = RabbitMQConfig.QUEUE_ENROLL)
    public void readEnrollData(@Payload EnrollRequestDTO message) {
        EnrollEntity enrollEntity = enrollMapper.toEntity(enrollMapper.toModel(message));
        log.info("Message from EVALUATION microservice (ENROLL ENTITY): {}", message);
        EnrollResponseDTO enrollResponseDTO = enrollMapper.toDTO(enrollMapper.toModel(enrollRepository.save(enrollEntity)));
        if(enrollResponseDTO != null){
            log.info("Database updated with new enroll data. Data {}", enrollResponseDTO);
        }else{
            log.info("There are some errors with the database.");
        }
    }

    //TODO: Create the logic to save an evaluation in this microservice database.
    @RabbitListener(queues = RabbitMQConfig.QUEUE_EVALUATION)
    public void readEvaluationData(@Payload EvaluationRequestDTO message){

//        EvaluationEntity evaluation = evaluationMapper
        log.info("Message from EVALUATION microservice (EVALUATION ENTITY): {}", message);
    }
}
