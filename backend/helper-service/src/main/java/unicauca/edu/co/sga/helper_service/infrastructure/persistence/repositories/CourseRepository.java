package unicauca.edu.co.sga.helper_service.infrastructure.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import unicauca.edu.co.sga.helper_service.infrastructure.persistence.entities.CourseEntity;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<CourseEntity, Long> {
    List<CourseEntity> findBySubjectId(Long subjectId);
    List<CourseEntity> findByTeacherId(Long teacherId);
    List<CourseEntity> findByRaId(Long raId);
}
