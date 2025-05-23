package unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import unicauca.edu.co.sga.evaluation_service.application.dto.response.stats.HistogramRowDTO;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.entities.EvaluationEntity;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.repositories.stats.CriterioHistogramaRepository;
import unicauca.edu.co.sga.evaluation_service.application.dto.response.stats.EvaluationStats;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
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

    //SELECCIONA LA PUNTUACION PARA CADA ESTUDIANTE

    //@Query("SELECT s.name FROM SubjectEntity s JOIN s.course c WHERE c.id = :courseId")
    //String findNameByCourseId(@Param("courseId") Long courseId);

    @Query("""
    SELECT new unicauca.edu.co.sga.evaluation_service.application.dto.response.stats.EvaluationStats(e.score, ra.name)
    FROM EvaluationEntity e
    JOIN e.enroll en
    JOIN en.course co
    JOIN co.subject s
    JOIN e.rubric r
    JOIN r.ra ra
    WHERE (:subjectName IS NULL OR s.name = :subjectName)
    AND (:rubricName IS NULL OR r.nombreRubrica = :rubricName)
    AND (:period IS NULL OR en.semester = :period)
    AND e.score IS NOT NULL
    """)
    List<EvaluationStats> findStatsByFilters(@Param("rubricName") String rubricName,
                                             @Param("subjectName") String subjectName,
                                             @Param("period") String period);





    //PROMEDIO POR CRITERIO
    /*@Query("""
SELECT new unicauca.edu.co.sga.evaluation_service.application.dto.response.stats.HistogramRowDTO(
    'GLOBAL',  -- nombre ficticio de criterio
    c.level,
    COUNT(c)
)
FROM CalificationRegisterEntity c
JOIN c.evaluation e
JOIN e.rubric r
JOIN e.enroll en
JOIN en.course co
JOIN co.subject s
WHERE (:rubricName IS NULL OR r.nombreRubrica = :rubricName)
AND (:subjectName IS NULL OR s.name = :subjectName)
AND (:period IS NULL OR en.semester = :period)
GROUP BY c.level
""")
    List<HistogramRowDTO> getHistogramRawData(
            @Param("rubricName") String rubricName,
            @Param("subjectName") String subjectName,
            @Param("period") String period
    );*/

}


