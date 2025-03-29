package unicauca.edu.co.sga.evaluation_service.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import unicauca.edu.co.sga.evaluation_service.domain.enums.GeneralEnums;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TeacherRequestDTO {
    private String name;
    private Long identification;
    private String degree;
    private GeneralEnums.identificationType identificationType;
    private GeneralEnums.status status;
    private String teacherType;
}
