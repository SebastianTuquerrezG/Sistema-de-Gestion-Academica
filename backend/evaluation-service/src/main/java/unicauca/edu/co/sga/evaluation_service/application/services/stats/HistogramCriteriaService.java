package unicauca.edu.co.sga.evaluation_service.application.services.stats;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import unicauca.edu.co.sga.evaluation_service.application.dto.response.stats.FilterStatsDTO;
import unicauca.edu.co.sga.evaluation_service.application.dto.response.stats.HistogramByCriteriaDTO;
import unicauca.edu.co.sga.evaluation_service.application.dto.response.stats.HistogramRowDTO;
import unicauca.edu.co.sga.evaluation_service.domain.enums.CalificationEnums;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.entities.CalificationRegisterEntity;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.entities.CriteriaEntity;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.entities.EvaluationEntity;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.entities.RubricEntity;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.repositories.EvaluationRepository;

import java.util.*;

@Service
@RequiredArgsConstructor
public class HistogramCriteriaService {
    /*private final EvaluationRepository repository;

    public List<HistogramByCriteriaDTO> getHistogramByCriteria(FilterStatsDTO filters) {
        List<HistogramRowDTO> rawData = repository.getHistogramRawData(
                filters.getRubricName(),
                filters.getSubjectName(),
                filters.getPeriod()
        );

        Map<String, Map<CalificationEnums, Long>> grouped = new HashMap<>();

        for (HistogramRowDTO row : rawData) {
            String criterio = row.getCriteriaName();
            String levelStr = row.getLevel();

            CalificationEnums level;
            try {
                level = CalificationEnums.valueOf(levelStr.replace(" ", "_").toUpperCase());
            } catch (IllegalArgumentException e) {
                continue;
            }

            grouped
                    .computeIfAbsent(criterio, k -> initEmptyLevelMap())
                    .merge(level, row.getCount(), Long::sum);
        }

        return grouped.entrySet().stream()
                .map(entry -> new HistogramByCriteriaDTO(entry.getKey(), entry.getValue()))
                .toList();
    }

    private Map<CalificationEnums, Long> initEmptyLevelMap() {
        Map<CalificationEnums, Long> map = new EnumMap<>(CalificationEnums.class);
        for (CalificationEnums level : CalificationEnums.values()) {
            map.put(level, 0L);
        }
        return map;
    }*/

}
