package unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import unicauca.edu.co.sga.evaluation_service.application.dto.response.stats.CriteriaAverageDTO;
import unicauca.edu.co.sga.evaluation_service.application.dto.response.stats.HistogramByCriteriaDTO;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.entities.EvaluationEntity;
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

    //HISTOGRAMA POR CRITERIO

    @Query(
            """
                    SELECT new unicauca.edu.co.sga.evaluation_service.application.dto.response.stats.HistogramByCriteriaDTO(
                                        
                        c.idCriterio,
                                        
                        c.crfDescripcion,
                                        
                        SUM(CASE WHEN e.score BETWEEN 0 AND 2.9 THEN 1 ELSE 0 END),
                                        
                        SUM(CASE WHEN e.score BETWEEN 3.0 AND 4.5 THEN 1 ELSE 0 END),
                                        
                        SUM(CASE WHEN e.score BETWEEN 4.6 AND 5.0 THEN 1 ELSE 0 END)
                                        
                    )
                                        
                    FROM CriteriaEntity c
                                        
                    JOIN c.rubric r
                    JOIN EvaluationEntity e ON e.rubric = r
                    JOIN e.enroll en
                    JOIN en.course co
                    JOIN co.subject s
                                        
                    WHERE (:subjectName IS NULL OR s.name = :subjectName)
                      AND (:rubricName IS NULL OR r.nombreRubrica = :rubricName)
                      AND (:period IS NULL OR en.semester = :period)
                                        
                    GROUP BY c.idCriterio, c.crfDescripcion
                                        
                    """
    )
    List<HistogramByCriteriaDTO> findHistogramByCriteria(
            @Param("subjectName") String subjectName,
            @Param("rubricName") String rubricName,
            @Param("period") String period
    );

    //PROMEDIO POR CRITERIO

    @Query(
            """
SELECT new unicauca.edu.co.sga.evaluation_service.application.dto.response.stats.CriteriaAverageDTO(
    c.idCriterio,
    c.crfDescripcion,
    AVG(cr.calification)
)

            FROM CriteriaEntity c
            JOIN c.rubric r
            JOIN EvaluationEntity e ON e.rubric = r
            JOIN CalificationRegisterEntity cr ON cr.evaluation = e AND cr.criterio = c
            JOIN e.enroll en
            JOIN en.course co
            JOIN co.subject s
            WHERE (:subjectName IS NULL OR s.name = :subjectName)
              AND (:rubricName IS NULL OR r.nombreRubrica = :rubricName)
              AND (:period IS NULL OR en.semester = :period)
            GROUP BY c.idCriterio, c.crfDescripcion
            """
    )
    List<CriteriaAverageDTO> findCriteriaAverages(
            @Param("subjectName") String subjectName,
            @Param("rubricName") String rubricName,
            @Param("period") String period
    );


}


