package unicauca.edu.co.sga.evaluation_service.application.ports;

import unicauca.edu.co.sga.evaluation_service.application.dto.response.stats.CourseStatsDTO;
import unicauca.edu.co.sga.evaluation_service.application.dto.response.stats.FilterStatsDTO;

public interface StatsPort {

    CourseStatsDTO getStatsByRubric(FilterStatsDTO filter);
}
