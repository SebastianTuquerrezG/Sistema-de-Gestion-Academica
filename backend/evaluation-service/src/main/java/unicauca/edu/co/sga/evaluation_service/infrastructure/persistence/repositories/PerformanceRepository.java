package unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.entities.PerformanceEntity;

import java.util.Optional;

@Repository
public interface PerformanceRepository extends JpaRepository<PerformanceEntity, Long> {
    Optional<PerformanceEntity> findByNameContainingIgnoreCase(String name);
}
