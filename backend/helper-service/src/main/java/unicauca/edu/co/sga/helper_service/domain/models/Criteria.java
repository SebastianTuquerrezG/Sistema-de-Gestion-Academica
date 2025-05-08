package unicauca.edu.co.sga.helper_service.domain.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * @apiNote Model of the Criteria Class to use in different part of the application.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Criteria {
    private Long id;
    private String description;
    private Long score;
    private Set<Long> performance_level;
}
