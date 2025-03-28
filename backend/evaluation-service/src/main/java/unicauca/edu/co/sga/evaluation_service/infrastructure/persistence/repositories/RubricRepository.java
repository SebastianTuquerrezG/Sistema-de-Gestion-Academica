package unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import unicauca.edu.co.sga.evaluation_service.domain.enums.GeneralEnums;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.entities.RubricEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface RubricRepository extends JpaRepository<RubricEntity, Long> {
    Optional<RubricEntity> findByNameContainingIgnoreCase(String name);
    List<RubricEntity> findBySubjectId(Long id);
    List<RubricEntity> findByRaId(Long id);
    List<RubricEntity> findByCriteriaId(Long id);
    List<RubricEntity> findByStatus(GeneralEnums.status status);

}
