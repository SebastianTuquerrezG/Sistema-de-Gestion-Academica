package unicauca.edu.co.sga.evaluation_service.application.ports;

import unicauca.edu.co.sga.evaluation_service.application.dto.response.stats.CourseStatsDTO;
import unicauca.edu.co.sga.evaluation_service.application.dto.response.stats.FilterStatsDTO;
import unicauca.edu.co.sga.evaluation_service.domain.models.stats.RubricStats;
import unicauca.edu.co.sga.evaluation_service.domain.models.stats.StudentProgress;

public interface StatsPort {

    CourseStatsDTO getStatsByRubric(FilterStatsDTO filter);

    //RubricStats calculateRubricStats(String rubricId); //borrar
    //StudentProgress getStudentProgress(String studentId); //borrar
}
