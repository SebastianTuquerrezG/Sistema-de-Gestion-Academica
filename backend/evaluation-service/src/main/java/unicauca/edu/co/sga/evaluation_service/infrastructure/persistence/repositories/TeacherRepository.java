package unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.entities.TeacherEntity;

@Repository
public interface TeacherRepository extends CrudRepository<TeacherEntity, Long> {
}
