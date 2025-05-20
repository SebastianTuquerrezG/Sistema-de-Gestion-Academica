package unicauca.edu.co.sga.evaluation_service.application.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.entities.RubricEntity;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.repositories.RubricRepository;

@Service
@RequiredArgsConstructor
public class RubricIntegrationService {
    private final RubricRepository rubricRepository;

    public RubricEntity createRubric(RubricEntity rubric) {
        return rubricRepository.save(rubric);
    }
}
