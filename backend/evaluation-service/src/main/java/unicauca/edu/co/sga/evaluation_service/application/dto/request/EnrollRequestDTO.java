package unicauca.edu.co.sga.evaluation_service.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EnrollRequestDTO {

    @NotNull(message = "El estudiante es obligatorio.")
    private Long student;

    @NotNull(message = "El curso es obligatorio.")
    private Long course;

    @NotNull(message = "El semestre es obligatorio.")
    private String semester;
}
