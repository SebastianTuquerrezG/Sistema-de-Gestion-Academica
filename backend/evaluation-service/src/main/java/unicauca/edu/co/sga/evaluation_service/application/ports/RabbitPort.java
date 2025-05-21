package unicauca.edu.co.sga.evaluation_service.application.ports;

import unicauca.edu.co.sga.evaluation_service.application.dto.request.EnrollRequestDTO;
import unicauca.edu.co.sga.evaluation_service.application.dto.request.EvaluationRequestDTO;

public interface RabbitPort {
    void sendEnroll(EnrollRequestDTO enroll);
    void sendEvaluation(EvaluationRequestDTO evaluation);
}
