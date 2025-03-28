package unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.entities.EnrollEntity;

import java.util.List;

@Repository
public interface EnrollRepository extends JpaRepository<EnrollEntity, Long> {
    List<EnrollEntity> findByStudentId(Long studentId);
    List<EnrollEntity> findByCourseId(Long courseId);
    List<EnrollEntity> findBySemester(String semester);
}
