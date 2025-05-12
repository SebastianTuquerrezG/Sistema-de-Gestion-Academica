package unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.entities.EvaluationEntity;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "calification_register")
public class CalificationRegisterEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true, updatable = false, name = "calification_id")
    private Long id;

    @Column(nullable = false)
    private Double calification;

    @Column(nullable = false, length = 300)
    private String message;

    @Column(nullable = false, length = 100)
    private String level; //As an example: "Nivel 1", "Nivel 2"

    @ManyToOne
    @JoinColumn(name = "evaluation_id", nullable = false, foreignKey = @ForeignKey(name = "fk_evaluation"))
    @JsonBackReference("evaluation-calification")
    private EvaluationEntity evaluation;
}
