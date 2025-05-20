package unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import unicauca.edu.co.sga.evaluation_service.domain.enums.GeneralEnums;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.entities.SubjectEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubjectRepository extends JpaRepository<SubjectEntity, Long> {
    Optional<SubjectEntity> findByNameContainingIgnoreCase(String name);
    List<SubjectEntity> findByStatus(GeneralEnums.status status);

    @Query("SELECT s.name FROM SubjectEntity s JOIN s.course c WHERE c.id = :courseId")
    String findSubjectNameByCourseId(@Param("courseId") Long courseId);
}
