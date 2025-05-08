package unicauca.edu.co.sga.helper_service.application.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import unicauca.edu.co.sga.helper_service.infrastructure.persistence.entities.RubricEntity;
import unicauca.edu.co.sga.helper_service.infrastructure.persistence.repositories.RubricRepository;

@Service
public class RubricIntegrationService {
    private final RubricRepository rubricRepository;

    @Autowired
    public RubricIntegrationService(RubricRepository rubricRepository) {
        this.rubricRepository = rubricRepository;
    }

    public RubricEntity createRubric(RubricEntity rubric) {
        return rubricRepository.save(rubric);
    }
}
