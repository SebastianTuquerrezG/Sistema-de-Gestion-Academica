package unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import unicauca.edu.co.sga.evaluation_service.domain.enums.CalificationEnums;

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

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CalificationEnums level;

    @ManyToOne
    @JoinColumn(name = "evaluation_id", nullable = false, foreignKey = @ForeignKey(name = "fk_evaluation"))
    @JsonBackReference
    private EvaluationEntity evaluation;
}
