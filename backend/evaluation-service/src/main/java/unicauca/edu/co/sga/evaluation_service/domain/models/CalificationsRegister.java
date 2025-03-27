package unicauca.edu.co.sga.evaluation_service.domain.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import unicauca.edu.co.sga.evaluation_service.domain.enums.CalificationEnums;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CalificationsRegister {
    private Long id;
    private Double calification;
    private String message;
    private int level;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "evaluationId", nullable = false, foreignKey = @ForeignKey(name = "fk_evaluation"))
    @JsonBackReference
    private Evaluation evaluation;

}
