package unicauca.edu.co.sga.evaluation_service.domain.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @apiNote Model of the Performance Class to use in different part of the application.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PerformanceLevel {
    private Long id;
    private Long idCriterio;
    private String description;
    private String rangoNota; // This could be changed for other
}
