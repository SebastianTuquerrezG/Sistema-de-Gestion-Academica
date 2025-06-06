package unicauca.edu.co.sga.evaluation_service.application.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import unicauca.edu.co.sga.evaluation_service.adapters.messaging.RabbitMQProducer;
import unicauca.edu.co.sga.evaluation_service.application.dto.request.EnrollRequestDTO;
import unicauca.edu.co.sga.evaluation_service.application.dto.request.EvaluationRequestDTO;
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
    public void sendEnroll(EnrollRequestDTO enroll) {
        rabbit.sendMessage(RabbitMQConfig.EXCHANGE, RabbitMQConfig.ROUTING_KEY_ENROLL, enroll);
    }

    @Override
    public void sendUpdateEnroll(EnrollEntity enroll) {
        rabbit.sendMessage(RabbitMQConfig.EXCHANGE, RabbitMQConfig.ROUTING_KEY_UPDATE_ENROLL, enroll);
    }

    @Override
    public void sendDeleteEnroll(EnrollEntity enroll) {
        rabbit.sendMessage(RabbitMQConfig.EXCHANGE, RabbitMQConfig.ROUTING_KEY_DELETE_ENROLL, enroll);
    }

    @Override
    public void sendEvaluation(EvaluationRequestDTO evaluation) {
        rabbit.sendMessage(RabbitMQConfig.EXCHANGE, RabbitMQConfig.ROUTING_KEY_EVALUATION, evaluation);
    }

    @Override
    public void sendUpdateEvaluation(EvaluationEntity evaluation) {
        rabbit.sendMessage(RabbitMQConfig.EXCHANGE, RabbitMQConfig.ROUTING_KEY_UPDATE_EVALUATION, evaluation);
    }

    @Override
    public void sendDeleteEvaluation(EvaluationEntity evaluation) {
        rabbit.sendMessage(RabbitMQConfig.EXCHANGE, RabbitMQConfig.ROUTING_KEY_DELETE_EVALUATION, evaluation);
    }
}
