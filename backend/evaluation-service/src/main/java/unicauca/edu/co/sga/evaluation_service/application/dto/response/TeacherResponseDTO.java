package unicauca.edu.co.sga.evaluation_service.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import unicauca.edu.co.sga.evaluation_service.domain.enums.GeneralEnums;
import unicauca.edu.co.sga.evaluation_service.domain.enums.TeacherEnums;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TeacherResponseDTO {
    private Long id;
    private String name;
    private Long identification;
    private String degree;
    private TeacherEnums teacherType;
    private GeneralEnums.status status;
}
