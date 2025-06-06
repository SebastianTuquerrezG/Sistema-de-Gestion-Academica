package unicauca.edu.co.sga.evaluation_service.application.ports;

import unicauca.edu.co.sga.evaluation_service.application.dto.request.EnrollRequestDTO;
import unicauca.edu.co.sga.evaluation_service.application.dto.request.EvaluationRequestDTO;
import unicauca.edu.co.sga.evaluation_service.application.dto.response.EnrollResponseDTO;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.entities.EnrollEntity;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.entities.EvaluationEntity;

public interface RabbitPort {
    void sendEnroll(EnrollRequestDTO enroll);
    void sendUpdateEnroll(EnrollEntity enroll);
    void sendDeleteEnroll(EnrollEntity enroll);
    void sendEvaluation(EvaluationRequestDTO evaluation);
    void sendUpdateEvaluation(EvaluationEntity evaluation);
    void sendDeleteEvaluation(EvaluationEntity evaluation);
}
