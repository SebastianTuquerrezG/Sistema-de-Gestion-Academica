package unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.repositories;

import java.util.List;

public interface CustomCalificationRegisterRepository {
    List<Object[]> countStudentsByLevelForCriterion(Long criterionId);
}