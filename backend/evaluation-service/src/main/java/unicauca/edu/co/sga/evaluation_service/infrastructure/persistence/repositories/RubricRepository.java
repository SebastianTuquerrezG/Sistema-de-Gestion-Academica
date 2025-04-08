package unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import unicauca.edu.co.sga.evaluation_service.domain.enums.GeneralEnums;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.entities.RubricEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface RubricRepository  extends JpaRepository<RubricEntity, Long> {
    Optional<RubricEntity> findByIdRubrica(Long id);
    Optional<RubricEntity> findByNombreRubricaContainingIgnoreCase(String name);
    List<RubricEntity> findBySubjectId(Long id);
    List<RubricEntity> findByRaId(Long id);
    //List<RubricEntity> findByIdCriterio(Long id);
    List<RubricEntity> findByEstado(GeneralEnums.status status);


//    @Query("SELECT r.study_objective FROM RubricEntity r " +
//            "JOIN r.subject sub " +
//            "JOIN sub.course c " +
//            "JOIN c.enroll e " +
//            "JOIN e.student s " +
//            "WHERE r.idRubrica = :rubricId " +
//            "AND sub.id = :subjectId " +
//            "AND s.id = :studentId " +
//            "AND e.semester = :semester")
//    Optional<String> findRubricDescriptionByStudent(
//            @Param("rubricId") Long rubricId,
//            @Param("subjectId") Long subjectId,
//            @Param("studentId") Long studentId,
//            @Param("semester") String semester);

}
