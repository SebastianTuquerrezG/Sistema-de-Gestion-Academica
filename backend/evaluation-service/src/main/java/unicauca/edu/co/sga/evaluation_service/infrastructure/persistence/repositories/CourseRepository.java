package unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.entities.CourseEntity;

@Repository
public interface CourseRepository extends CrudRepository<CourseEntity, Long> {
}
