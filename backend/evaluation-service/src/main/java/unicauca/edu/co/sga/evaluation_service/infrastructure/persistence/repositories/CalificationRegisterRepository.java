package unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.entities.CalificationRegisterEntity;

import java.util.List;

@Repository
public interface CalificationRegisterRepository extends JpaRepository<CalificationRegisterEntity, Long> {
    List<CalificationRegisterEntity> findByLevel(String level);
    List<CalificationRegisterEntity> findByEvaluationId(Long id);

//    @Query("SELECT cr FROM CalificationRegisterEntity cr " +
//            "JOIN cr.evaluation e " +
//            "WHERE e.id = :evaluationId")
//    List<CalificationRegisterEntity> findByEvaluationId2(@Param("evaluationId") Long evaluationId);


    @Query("SELECT cr FROM CalificationRegisterEntity cr " +
            "JOIN cr.evaluation e " +
            "JOIN e.enroll en " +
            "JOIN en.student s " +
            "JOIN en.course co " +
            "JOIN co.subject sub " +
            "JOIN e.rubric r " +
            "WHERE s.id = :studentId " +
            "AND sub.id = :subjectId " +
            "AND en.semester = :semester " +
            "AND r.idRubrica = :rubricId")
    List<CalificationRegisterEntity> findCalificationsByStudentAndSubject(
            @Param("studentId") Long studentId,
            @Param("subjectId") Long subjectId,
            @Param("semester") String semester,
            @Param("rubricId") Long rubricId);
}

