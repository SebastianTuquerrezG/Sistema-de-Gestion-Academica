package unicauca.edu.co.sga.evaluation_service.domain.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import unicauca.edu.co.sga.evaluation_service.domain.enums.EvaluationStatus;

/**
 * Entity for enroll a course with a student and the evaluation
 */

@Entity
@Table(name = "enrollSubjectClass")
@Data
@Builder
public class EnrollSubjectClass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true, updatable = false)
    private int id;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "courseID", nullable = false, foreignKey = @ForeignKey(name = "fk_course"))
    @JsonBackReference
    private Course course;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "studentID", nullable = false, foreignKey = @ForeignKey(name = "fk_student"))
    @JsonBackReference
    private Student student;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EvaluationStatus.evaluation status;
}
