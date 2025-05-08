package unicauca.edu.co.sga.evaluation_service.application.ports;

import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.entities.EnrollEntity;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.entities.EvaluationEntity;

public interface RabbitPort {
    void sendEnroll(EnrollEntity enroll);
    void sendEvaluation(EvaluationEntity evaluation);
}
