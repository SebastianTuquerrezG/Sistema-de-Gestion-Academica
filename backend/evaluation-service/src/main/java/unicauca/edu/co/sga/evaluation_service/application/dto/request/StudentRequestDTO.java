package unicauca.edu.co.sga.evaluation_service.application.dto.request;

import jakarta.validation.constraints.NotNull;
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
public class StudentRequestDTO {
    @NotNull(message = "El nombre del estudiante es obligatorio")
    private String name;

    @NotNull(message = "La identificacion es obligatoria")
    private Long identification;

    @NotNull(message = "El tipo de identificacion es obligatoria")
    private GeneralEnums.identificationType type;
}
