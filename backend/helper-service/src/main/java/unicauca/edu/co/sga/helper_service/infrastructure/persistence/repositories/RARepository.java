package unicauca.edu.co.sga.helper_service.infrastructure.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import unicauca.edu.co.sga.helper_service.infrastructure.persistence.entities.RAEntity;

@Repository
public interface RARepository extends JpaRepository<RAEntity, Long> {
}
