package unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import unicauca.edu.co.sga.evaluation_service.application.dto.response.StudentView.CriteriaResponseViewDTO;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.entities.CalificationRegisterEntity;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.entities.CriteriaEntity;

import java.util.List;

@Repository
public interface CriteriaRepository extends JpaRepository<CriteriaEntity, Long> {
    //List<CriteriaEntity> findByPerformanceLevelId(Long performanceLevelId);
    @Query("SELECT c FROM CriteriaEntity c " +
            "WHERE c.rubric.id = :rubricId")
    List<CriteriaResponseViewDTO> findByRubricId(@Param("rubricId") Long rubricId);
    @Query("SELECT DISTINCT c FROM CriteriaEntity c " +
            "LEFT JOIN FETCH c.levels " +
            "JOIN c.rubric r " +
            "JOIN r.subject sub " +
            "JOIN sub.course co " +
            "JOIN co.enroll en " +
            "JOIN en.student s " +
            "WHERE r.id = :rubricId " +
            "AND s.id = :studentId " +
            "AND sub.id = :subjectId " +
            "AND en.semester = :semester")
    List<CriteriaEntity> findByRubricAndStudentAndSubject(
            @Param("rubricId") Long rubricId,
            @Param("studentId") Long studentId,
            @Param("subjectId") Long subjectId,
            @Param("semester") String semester);
 /* @Query("SELECT DISTINCT c FROM CriteriaEntity c " +
          "LEFT JOIN FETCH c.levels " +
          "JOIN c.rubric r " +
          "JOIN r.subject s " +
          "JOIN r.ra ra " +
          "JOIN ra.course co " +
          "JOIN co.enroll en " +
          "JOIN en.student st " +
          "WHERE c.rubric.id = :rubricId " +
          "AND s.id = :subjectId " +
          "AND st.id = :studentId " +
          "AND en.semester = :semester")
  List<CriteriaEntity> findByRubricAndStudentAndSubject(
          @Param("rubricId") Long rubricId,
          @Param("studentId") Long studentId,
          @Param("subjectId") Long subjectId,
          @Param("semester") String semester);
*/
}
