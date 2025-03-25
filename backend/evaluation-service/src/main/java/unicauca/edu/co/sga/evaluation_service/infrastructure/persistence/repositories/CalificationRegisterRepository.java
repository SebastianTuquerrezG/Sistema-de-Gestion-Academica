package unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.entities.CalificationRegisterEntity;

@Repository
public interface CalificationRegisterRepository extends JpaRepository<CalificationRegisterEntity, Long> {
}
