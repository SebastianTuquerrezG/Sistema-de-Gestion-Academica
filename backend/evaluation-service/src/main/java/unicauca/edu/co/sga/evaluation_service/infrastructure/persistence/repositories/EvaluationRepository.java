package unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.repositories;

import unicauca.edu.co.sga.evaluation_service.domain.models.Evaluation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.entities.EvaluationEntity;

import java.util.List;
@Repository
public interface EvaluationRepository extends JpaRepository<EvaluationEntity, Long>{

}
