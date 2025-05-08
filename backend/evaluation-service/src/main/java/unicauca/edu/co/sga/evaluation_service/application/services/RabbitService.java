package unicauca.edu.co.sga.evaluation_service.application.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import unicauca.edu.co.sga.evaluation_service.adapters.messaging.RabbitMQProducer;
import unicauca.edu.co.sga.evaluation_service.application.ports.RabbitPort;
import unicauca.edu.co.sga.evaluation_service.infrastructure.config.RabbitMQConfig;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.entities.EnrollEntity;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.entities.EvaluationEntity;

@Service
@Transactional
@RequiredArgsConstructor
public class RabbitService implements RabbitPort {
    private final RabbitMQProducer rabbit;

    // TODO: With those methods we need to use it into each service when they do a CRUD.
    // EXAMPLE: Enroll create a new tuple, then send with rabbit from EnrollService, the entity.

    @Override
    public void sendEnroll(EnrollEntity enroll) {
        rabbit.sendMessage(RabbitMQConfig.EXCHANGE, RabbitMQConfig.ROUTING_KEY_ENROLL, enroll);
    }

    @Override
    public void sendEvaluation(EvaluationEntity evaluation) {
        rabbit.sendMessage(RabbitMQConfig.EXCHANGE, RabbitMQConfig.ROUTING_KEY_ENROLL, evaluation);
    }
}
