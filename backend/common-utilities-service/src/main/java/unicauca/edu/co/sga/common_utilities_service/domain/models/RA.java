package unicauca.edu.co.sga.common_utilities_service.domain.models;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RA {
    private Long id;
    private Set<Long> course;
}
