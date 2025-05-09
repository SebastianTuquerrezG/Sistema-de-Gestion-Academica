package unicauca.edu.co.sga.helper_service.adapters.messaging;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import unicauca.edu.co.sga.helper_service.infrastructure.config.RabbitMQConfig;
import unicauca.edu.co.sga.helper_service.infrastructure.persistence.entities.EnrollEntity;
import unicauca.edu.co.sga.helper_service.infrastructure.persistence.entities.EvaluationEntity;

@Service
@RequiredArgsConstructor
@Slf4j
public class RabbitMQConsumer {
    @RabbitListener(queues = RabbitMQConfig.QUEUE_ENROLL)
    public void readEnrollData(EnrollEntity enroll){
        log.info("Message from EVALUATION microservice (ENROLL ENTITY): {}", enroll);
    }

    @RabbitListener(queues = RabbitMQConfig.QUEUE_EVALUATION)
    public void readEvaluationData(EvaluationEntity evaluation){
        log.info("Message from EVALUATION microservice (EVALUATION ENTITY): {}", evaluation);
    }
}
