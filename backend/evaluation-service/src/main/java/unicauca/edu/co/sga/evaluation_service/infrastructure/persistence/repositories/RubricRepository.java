package unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.entities.RubricEntity;

public interface RubricRepository extends JpaRepository<RubricEntity, Long> {
}
