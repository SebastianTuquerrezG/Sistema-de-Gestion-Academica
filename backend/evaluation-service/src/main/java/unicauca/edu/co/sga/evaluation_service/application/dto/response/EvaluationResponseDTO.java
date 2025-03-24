package unicauca.edu.co.sga.evaluation_service.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EvaluationResponseDTO {
    private Long id;
    private Long enroll;
    private Long rubric;
    private String description;
    private Date created_at;
    private Date updated_at;

    public EvaluationResponseDTO(Long id, Long enroll, Long rubric, String description) {
        this.id = id;
        this.enroll = enroll;
        this.rubric = rubric;
        this.description = description;
    }
}
