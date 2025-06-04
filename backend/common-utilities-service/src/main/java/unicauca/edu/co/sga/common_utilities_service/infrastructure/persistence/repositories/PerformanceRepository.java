package unicauca.edu.co.sga.common_utilities_service.infrastructure.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import unicauca.edu.co.sga.common_utilities_service.infrastructure.persistence.entities.PerformanceEntity;

@Repository
public interface PerformanceRepository extends JpaRepository<PerformanceEntity, Long> {
//    Optional<PerformanceEntity> findByNameContainingIgnoreCase(String name);
}
