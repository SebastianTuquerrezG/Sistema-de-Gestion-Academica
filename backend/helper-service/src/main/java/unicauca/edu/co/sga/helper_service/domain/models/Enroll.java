package unicauca.edu.co.sga.helper_service.domain.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Model for enroll a course with a student and the evaluation
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Enroll {
    private Long id;
    private Long course;
    private Long student;
    private String semester;
}
