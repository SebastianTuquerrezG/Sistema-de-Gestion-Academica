package unicauca.edu.co.sga.evaluation_service.domain.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import unicauca.edu.co.sga.evaluation_service.domain.enums.GeneralEnums;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Rubric {
    private Long id;
    private String name;
    private String study_objective;
    private String competence;
    private Set<Long> subject;
    private Long ra;
    private Set<Long> criteria;
    private GeneralEnums.status status;
    private Set<Long> evaluation;
}
