package unicauca.edu.co.sga.helper_service.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseRequestDTO {

    @NotNull(message = "El profesor encargado es obligatorio.")
    private Long teacher;

    @NotNull(message = "La materia asignada es obligatoria.")
    private Long subject;

    @NotNull(message = "El resultado de aprendizaje del curso es obligatoria.")
    private Long ra;
}
