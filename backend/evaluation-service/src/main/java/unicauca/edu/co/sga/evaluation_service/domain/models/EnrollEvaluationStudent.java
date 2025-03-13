package unicauca.edu.co.sga.evaluation_service.domain.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

@Entity
@Table(name = "enrollEvaluationStudent")
@Data
@Builder
public class EnrollEvaluationStudent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true, updatable = false)
    private int id;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "studentID", nullable = false, foreignKey = @ForeignKey(name = "fk_student"))
    @JsonBackReference
    private Student student;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "evaluationID", nullable = false, foreignKey = @ForeignKey(name = "fk_evaluation"))
    @JsonBackReference
    private Evaluation evaluation;
}
