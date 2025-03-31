package unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import unicauca.edu.co.sga.evaluation_service.application.dto.response.StudentView.CourseResponseViewDTO;
import unicauca.edu.co.sga.evaluation_service.application.dto.response.StudentView.RubricResponseViewDTO;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.entities.EnrollEntity;

import java.util.List;

@Repository
public interface EnrollRepository extends JpaRepository<EnrollEntity, Long> {
    List<EnrollEntity> findByStudentId(Long studentId);
    List<EnrollEntity> findByCourseId(Long courseId);
    List<EnrollEntity> findBySemester(String semester);

    @Query("SELECT e.course, t FROM EnrollEntity e " +
            "JOIN e.course c " +
            "JOIN c.teacher t " +
            "WHERE e.student.id = :studentId AND e.semester = :semester")
    List<CourseResponseViewDTO> findCoursesAndTeachersByStudentAndSemester(@Param("studentId") Long studentId, @Param("semester") String semester);

    @Query("SELECT r.name, r.created_at FROM EnrollEntity e " +
            "JOIN e.course c " +
            "JOIN c.ra ra " +
            "JOIN ra.rubric r " +
            "WHERE e.student.id = :studentId " +
            "AND e.course.id = :courseId " +
            "AND e.semester = :semester")
    List<RubricResponseViewDTO> findRubricNamesAndDates(
            @Param("studentId") Long studentId,
            @Param("courseId") Long courseId,
            @Param("semester") String semester
    );

}
