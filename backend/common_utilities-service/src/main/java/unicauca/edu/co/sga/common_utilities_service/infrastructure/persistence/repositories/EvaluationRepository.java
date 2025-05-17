package unicauca.edu.co.sga.common_utilities_service.infrastructure.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import unicauca.edu.co.sga.common_utilities_service.infrastructure.persistence.entities.EvaluationEntity;

import java.util.List;

@Repository
public interface EvaluationRepository extends JpaRepository<EvaluationEntity, Long> {
    Boolean existsByEnrollIdAndRubric_IdRubrica(Long enrollId, Long rubricId);
    List<EvaluationEntity> findByEnrollId(Long enrollId);
    List<EvaluationEntity> findByRubric_IdRubrica(Long rubricId);

    @Query("SELECT e FROM EvaluationEntity e " +
            "JOIN e.enroll en " +
            "JOIN en.student s " +
            "JOIN en.course co " +
            "JOIN co.subject sub " +
            "JOIN e.rubric r " +
            "WHERE s.id = :studentId " +
            "AND sub.id = :subjectId " +
            "AND en.semester = :semester " +
            "AND r.idRubrica = :rubricId")
    List<EvaluationEntity> findEvaluationsByStudentAndSubject(
            @Param("studentId") Long studentId,
            @Param("subjectId") Long subjectId,
            @Param("semester") String semester,
            @Param("rubricId") Long rubricId);

/*
    @Query("SELECT e FROM EvaluationEntity e " +
            "JOIN e.enroll en " +
            "JOIN en.student s " +
            "JOIN en.course co " +
            "JOIN co.subject sub " +
            "JOIN e.rubric r " +
            "WHERE s.id = :studentId " +
            "AND sub.id = :subjectId " +
            "AND en.semester = :semester " +
            "AND r.id = :rubricId")
    List<EvaluationEntity> findEvaluationsByStudentAndSubject(
            @Param("studentId") Long studentId,
            @Param("subjectId") Long subjectId,
            @Param("semester") String semester,
            @Param("rubricId") Long rubricId);*/
}


