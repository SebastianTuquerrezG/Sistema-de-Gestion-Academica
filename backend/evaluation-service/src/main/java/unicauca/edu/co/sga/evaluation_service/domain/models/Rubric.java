package unicauca.edu.co.sga.evaluation_service.domain.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Rubric {
    private Long id;
    private String name;
    private Set<Long> evaluation;
}
