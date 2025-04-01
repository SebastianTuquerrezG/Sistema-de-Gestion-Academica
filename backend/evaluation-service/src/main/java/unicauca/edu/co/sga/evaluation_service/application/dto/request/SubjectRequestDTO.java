package unicauca.edu.co.sga.evaluation_service.application.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import unicauca.edu.co.sga.evaluation_service.domain.enums.GeneralEnums;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubjectRequestDTO {
    @NotNull(message = "El nombre de la materia es obligatorio")
    private String name;

    @NotNull(message = "El numero de la creditos de la materia es obligatorio")
    private Integer credits;

    @NotNull(message = "Los objetivos de la materia son obligatorios")
    private String objectives;

    @NotNull(message = "El estado de la materia es obligatorio")
    private GeneralEnums.status status;
}
