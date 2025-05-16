package unicauca.edu.co.sga.evaluation_service.application.ports;

import unicauca.edu.co.sga.evaluation_service.domain.models.stats.RubricStats;
import unicauca.edu.co.sga.evaluation_service.domain.models.stats.StudentProgress;

public interface  StatsService {
    RubricStats calculateRubricStats(String rubricId);
    StudentProgress getStudentProgress(String studentId);
}
