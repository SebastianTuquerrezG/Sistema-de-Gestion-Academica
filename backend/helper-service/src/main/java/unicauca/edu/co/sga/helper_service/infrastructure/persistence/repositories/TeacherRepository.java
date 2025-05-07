package unicauca.edu.co.sga.helper_service.infrastructure.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import unicauca.edu.co.sga.helper_service.domain.enums.GeneralEnums;
import unicauca.edu.co.sga.helper_service.domain.enums.TeacherEnums;
import unicauca.edu.co.sga.helper_service.infrastructure.persistence.entities.TeacherEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeacherRepository extends JpaRepository<TeacherEntity, Long> {
    Optional<TeacherEntity> findByIdentification(Long identification);
    List<TeacherEntity> findByNameContainingIgnoreCase(String name);
    List<TeacherEntity> findByDegreeContainingIgnoreCase(String degree);
    List<TeacherEntity> findByIdentificationType(GeneralEnums.identificationType identifierType);
    List<TeacherEntity> findByStatus(GeneralEnums.status status);
    List<TeacherEntity> findByTeacherType(TeacherEnums teacherType);
    List<TeacherEntity> findByCourseId(Long courseId);
}
