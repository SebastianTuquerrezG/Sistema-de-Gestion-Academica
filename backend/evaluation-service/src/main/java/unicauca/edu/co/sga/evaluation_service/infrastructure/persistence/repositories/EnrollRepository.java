package unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import unicauca.edu.co.sga.evaluation_service.application.dto.response.StudentView.SubjectHeaderResponseViewDTO;
import unicauca.edu.co.sga.evaluation_service.application.dto.response.StudentView.SubjectResponseViewDTO;
import unicauca.edu.co.sga.evaluation_service.application.dto.response.StudentView.RubricResponseViewDTO;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.entities.EnrollEntity;

import java.util.List;

@Repository
public interface EnrollRepository extends JpaRepository<EnrollEntity, Long> {
    List<EnrollEntity> findByStudentId(Long studentId);
    List<EnrollEntity> findByCourseId(Long courseId);
    List<EnrollEntity> findBySemester(String semester);
    boolean existsByStudentIdAndCourseIdAndSemester(Long studentId, Long courseId, String semester);

    @Query("SELECT NEW unicauca.edu.co.sga.evaluation_service.application.dto.response.StudentView.SubjectResponseViewDTO(" +
            "s.name, t.name, s.id) " +
            "FROM EnrollEntity e " +
            "JOIN e.course c " +
            "JOIN c.subject s " +
            "JOIN c.teacher t " +
            "WHERE e.student.id = :studentId " +
            "AND e.semester = :semester")
    List<SubjectResponseViewDTO> findSubjectsAndTeachersByStudentAndSemester(@Param("studentId") Long studentId, @Param("semester") String semester);

    @Query("SELECT NEW unicauca.edu.co.sga.evaluation_service.application.dto.response.StudentView.SubjectHeaderResponseViewDTO(" +
            "s.name, t.name, e.semester) " +
            "FROM EnrollEntity e " +
            "JOIN e.course c " +
            "JOIN c.subject s " +
            "JOIN c.teacher t " +
            "WHERE e.student.id = :studentId " +
            "AND e.semester = :semester" +
            " AND s.id = :subjectId"
    )
    SubjectHeaderResponseViewDTO findSubjectAndTeachersByStudentAndSemester(@Param("studentId") Long studentId, @Param("semester") String semester , @Param("subjectId") Long subjectId);


    @Query("SELECT DISTINCT e.semester FROM EnrollEntity e WHERE e.student.id = :studentId")
    List<String> findDistinctSemestersByStudentId(@Param("studentId") Long studentId);


    @Query("SELECT new unicauca.edu.co.sga.evaluation_service.application.dto.response.StudentView.RubricResponseViewDTO(r.idRubrica, r.nombreRubrica) " +
            "FROM EnrollEntity e " +
            "JOIN e.course c " +
            "JOIN c.subject s " +
            "JOIN s.rubric r " +
            "WHERE e.student.id = :studentId " +
            "AND s.id = :subjectId " +
            "AND e.semester = :semester")
    List<RubricResponseViewDTO> findRubricNamesAndDates(
            @Param("studentId") Long studentId,
            @Param("subjectId") Long subjectId,
            @Param("semester") String semester
    );
//    @Query("SELECT new unicauca.edu.co.sga.evaluation_service.application.dto.response.StudentView.RubricResponseViewDTO(r.name, r.created_at) " +
//            "FROM EnrollEntity e " +
//            "JOIN e.course c " +
//            "JOIN c.subject s " +
//            "JOIN s.rubric r " +
//            "WHERE e.student.id = :studentId " +
//            "AND s.id = :subjectId " +
//            "AND e.semester = :semester")
//    List<RubricResponseViewDTO> findRubricNamesAndDatesBySubject(
//            @Param("studentId") Long studentId,
//            @Param("subjectId") Long subjectId,
//            @Param("semester") String semester
//    );


}
