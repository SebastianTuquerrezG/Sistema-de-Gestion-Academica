package unicauca.edu.co.sga.evaluation_service.domain.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import unicauca.edu.co.sga.evaluation_service.domain.enums.EvaluationStatus;

import java.util.Set;

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
    private Set<Long> evaluation;
    private String semester;
}
