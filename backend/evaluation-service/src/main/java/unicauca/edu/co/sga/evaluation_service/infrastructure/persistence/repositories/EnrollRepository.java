package unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.entities.EnrollEntity;

public interface EnrollRepository extends JpaRepository<EnrollEntity, Long> {
}
