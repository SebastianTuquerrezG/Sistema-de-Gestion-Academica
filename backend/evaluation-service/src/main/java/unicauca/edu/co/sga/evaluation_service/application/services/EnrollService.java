package unicauca.edu.co.sga.evaluation_service.application.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.entities.EnrollEntity;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.repositories.EnrollRepository;

@Service
public class EnrollService {
    private final EnrollRepository enrollRepository;

    @Autowired
    public EnrollService(EnrollRepository enrollRepository) {
        this.enrollRepository = enrollRepository;
    }

    public EnrollEntity createEnroll(EnrollEntity enroll) {
        return enrollRepository.save(enroll);
    }
}
