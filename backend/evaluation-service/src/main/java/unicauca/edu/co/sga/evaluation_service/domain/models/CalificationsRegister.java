package unicauca.edu.co.sga.evaluation_service.domain.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CalificationsRegister {
    private Long id;
    private Long criteriaId;
    private Double calification;
    private String message;
    private String level;
    private Long evaluation;
}
