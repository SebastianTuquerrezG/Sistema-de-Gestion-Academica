package unicauca.edu.co.sga.common_utilities_service.domain.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import unicauca.edu.co.sga.common_utilities_service.domain.enums.GeneralEnums;
import unicauca.edu.co.sga.common_utilities_service.domain.enums.TeacherEnums;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Teacher {
    private Long id;
    private String name;
    private Long identification;
    private String degree;
    private GeneralEnums.identificationType identificationType;
    private GeneralEnums.status status;
    private TeacherEnums teacherType;
    private Set<Course> course;
}
