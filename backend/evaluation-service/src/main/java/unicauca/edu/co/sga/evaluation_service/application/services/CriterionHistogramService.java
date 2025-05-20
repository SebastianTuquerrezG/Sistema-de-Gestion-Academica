package unicauca.edu.co.sga.evaluation_service.application.services;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import unicauca.edu.co.sga.evaluation_service.application.dto.response.stats.CriterionHistogramDTO;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.entities.CriteriaEntity;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.repositories.CalificationRegisterRepository;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.repositories.CriteriaRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CriterionHistogramService {

    private final CalificationRegisterRepository calificationRepo;
    private final CriteriaRepository criteriaRepo;

    public CriterionHistogramDTO getHistogramData(Long criterionId) {
        CriteriaEntity criterion = criteriaRepo.findById(criterionId)
                .orElseThrow(() -> new EntityNotFoundException("Criterio no encontrado"));

        List<Object[]> results = calificationRepo.countStudentsByLevelForCriterion(criterionId);

        Map<String, Integer> levelCounts = new HashMap<>();
        results.forEach(arr -> levelCounts.put((String) arr[0], ((Number) arr[1]).intValue()));

        return CriterionHistogramDTO.builder()
                .criterionId(criterionId)
                .criterionName(criterion.getCrfDescripcion())
                .levelCounts(levelCounts)
                .build();
    }
}
