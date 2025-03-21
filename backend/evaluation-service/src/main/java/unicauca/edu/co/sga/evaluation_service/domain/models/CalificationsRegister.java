package unicauca.edu.co.sga.evaluation_service.domain.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import unicauca.edu.co.sga.evaluation_service.domain.enums.CalificationEnums;

@Entity
@Table(name = "calificationsRegister")
@Data
@Builder
public class CalificationsRegister {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true, updatable = false)
    private int id;

    @Column(nullable = false)
    private float calification;

    @Column(nullable = false, length = 300)
    private String message;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CalificationEnums.level level;

    @ManyToOne
    @JoinColumn(name = "evaluationID", nullable = false, foreignKey = @ForeignKey(name = "fk_evaluation"))
    @JsonBackReference
    private Evaluation evaluation;


}
