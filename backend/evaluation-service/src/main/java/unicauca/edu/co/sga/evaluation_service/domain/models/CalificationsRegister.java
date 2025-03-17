package unicauca.edu.co.sga.evaluation_service.domain.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

@Entity
@Table(name = "calificationsRegister")
@Data
@Builder
public class CalificationsRegister {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true, updatable = false)
    private int id;

    private float calification;

    private String message;

    private int level;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "evaluationId", nullable = false, foreignKey = @ForeignKey(name = "fk_evaluation"))
    @JsonBackReference
    private Evaluation evaluation;


}
