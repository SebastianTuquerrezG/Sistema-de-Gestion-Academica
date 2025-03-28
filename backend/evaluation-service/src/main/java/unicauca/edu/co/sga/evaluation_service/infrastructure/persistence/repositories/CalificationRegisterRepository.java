package unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.entities.CalificationRegisterEntity;

import java.util.List;

@Repository
public interface CalificationRegisterRepository extends JpaRepository<CalificationRegisterEntity, Long> {
    List<CalificationRegisterEntity> findByLevel(String level);
    List<CalificationRegisterEntity> findByEvaluationId(Long id);
}
