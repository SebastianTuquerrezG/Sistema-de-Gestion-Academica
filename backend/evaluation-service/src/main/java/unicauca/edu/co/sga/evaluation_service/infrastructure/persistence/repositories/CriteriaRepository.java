package unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.entities.CriteriaEntity;

import java.util.List;

@Repository
public interface CriteriaRepository extends JpaRepository<CriteriaEntity, Long> {
    //List<CriteriaEntity> findByPerformanceLevelId(Long performanceLevelId);
}
