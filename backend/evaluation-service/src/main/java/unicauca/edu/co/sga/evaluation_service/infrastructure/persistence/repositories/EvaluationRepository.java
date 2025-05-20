package unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.entities.EvaluationEntity;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface EvaluationRepository extends JpaRepository<EvaluationEntity, Long> {
    Boolean existsByEnrollIdAndRubric_IdRubrica(Long enrollId, Long rubricId);
    List<EvaluationEntity> findByEnrollId(Long enrollId);
    List<EvaluationEntity> findByRubric_IdRubrica(Long rubricId);

    @Query("SELECT e.score FROM EvaluationEntity e " +
            "JOIN e.enroll en " +
            "JOIN en.student s " +
            "JOIN en.course co " +
            "JOIN co.subject sub " +
            "JOIN e.rubric r " +
            "WHERE s.id = :studentId " +
            "AND sub.id = :subjectId " +
            "AND en.semester = :semester " +
            "AND r.idRubrica = :rubricId")
    Optional<BigDecimal> findEvaluationsByStudentAndSubject(
            @Param("studentId") Long studentId,
            @Param("subjectId") Long subjectId,
            @Param("semester") String semester,
            @Param("rubricId") Long rubricId);

    @Query("""
        SELECT e.score 
        FROM EvaluationEntity e
        WHERE e.enroll.course.id = :courseId
        AND e.rubric.idRubrica = :rubricId
        AND e.enroll.semester = :semester
        """)
    List<BigDecimal> findScoresByCourseRubricAndSemester(
            @Param("courseId") Long courseId,
            @Param("rubricId") Long rubricId,
            @Param("semester") String semester);

    @Query("SELECT s.name FROM SubjectEntity s JOIN s.course c WHERE c.id = :courseId")
    String findNameByCourseId(@Param("courseId") Long courseId);
}


