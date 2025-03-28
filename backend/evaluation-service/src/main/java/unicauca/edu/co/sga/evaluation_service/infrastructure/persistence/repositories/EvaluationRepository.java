package unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.entities.EvaluationEntity;

import java.util.List;

@Repository
public interface EvaluationRepository extends JpaRepository<EvaluationEntity, Long> {
    List<EvaluationEntity> findByEnrollId(Long enrollId);
    List<EvaluationEntity> findByRubricId(Long rubricId);
}
