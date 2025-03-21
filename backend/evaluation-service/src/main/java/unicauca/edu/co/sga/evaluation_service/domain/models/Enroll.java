package unicauca.edu.co.sga.evaluation_service.domain.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import unicauca.edu.co.sga.evaluation_service.domain.enums.EvaluationStatus;

import java.util.Set;

/**
 * Entity for enroll a course with a student and the evaluation
 */

@Entity
@Table(name = "enrollSubjectClass")
@Data
@Builder
public class Enroll {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true, updatable = false)
    private int id;

    @ManyToOne
    @JoinColumn(name = "courseID", nullable = false, foreignKey = @ForeignKey(name = "fk_course"))
    @JsonBackReference
    private Course course;

    @ManyToOne
    @JoinColumn(name = "studentID", nullable = false, foreignKey = @ForeignKey(name = "fk_student"))
    @JsonBackReference
    private Student student;

    @OneToMany(mappedBy = "enroll", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Evaluation> evaluation;

    @Column(nullable = false)
    private int semester;
}
