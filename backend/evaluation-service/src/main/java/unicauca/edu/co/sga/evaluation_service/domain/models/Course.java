package unicauca.edu.co.sga.evaluation_service.domain.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Course {
    private Long id;
    private Set<Long> teacher;
    private Long subject;
    private Long ra;
    private Set<Long> enroll;
}
