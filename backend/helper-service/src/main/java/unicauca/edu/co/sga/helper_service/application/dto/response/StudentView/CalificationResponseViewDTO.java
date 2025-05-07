package unicauca.edu.co.sga.helper_service.application.dto.response.StudentView;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CalificationResponseViewDTO {

    private Double calification;
    private String message;
    private String level;

}
