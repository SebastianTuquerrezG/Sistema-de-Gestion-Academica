package unicauca.edu.co.sga.evaluation_service.application.services.stats;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import unicauca.edu.co.sga.evaluation_service.application.dto.response.stats.FilterStatsDTO;
import unicauca.edu.co.sga.evaluation_service.application.dto.response.stats.HistogramByCriteriaDTO;
import unicauca.edu.co.sga.evaluation_service.application.dto.response.stats.HistogramByCriteriaProjection;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.repositories.EvaluationRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HistogramCriteriaService {

    private final EvaluationRepository evaluationRepository;
    //private final ObjectMapper objectMapper;

    public List<HistogramByCriteriaDTO> getHistogramByCriteria(String subjectName, String rubricName, String period) {
        List<Object[]> criteriaData = evaluationRepository.findCriteriaWithActualLevels(
                subjectName, rubricName, period);

        Map<Long, HistogramByCriteriaDTO> result = new LinkedHashMap<>();

        // Primero organizamos todos los niveles por criterio
        Map<Long, List<LevelInfo>> levelsByCriteria = new HashMap<>();

        for (Object[] row : criteriaData) {
            Long criteriaId = (Long) row[0];
            String criteriaDescription = (String) row[1];
            Long levelId = (Long) row[2];
            String levelDescription = (String) row[3];
            Double minRango = (Double) row[4];
            Double maxRango = (Double) row[5];

            // Creamos el DTO si no existe
            result.computeIfAbsent(criteriaId, id ->
                    new HistogramByCriteriaDTO(id, criteriaDescription, new LinkedHashMap<>()));

            // Agregamos la info del nivel
            levelsByCriteria.computeIfAbsent(criteriaId, k -> new ArrayList<>())
                    .add(new LevelInfo(levelId, levelDescription, minRango, maxRango));
        }

        // Luego contamos estudiantes para cada nivel
        levelsByCriteria.forEach((criteriaId, levels) -> {
            HistogramByCriteriaDTO dto = result.get(criteriaId);

            levels.forEach(level -> {
                Long count = evaluationRepository.countStudentsByLevelRange(
                        criteriaId, level.minRango, level.maxRango,
                        subjectName, rubricName, period);

                String levelKey = String.format("%s (%.1f-%.1f)",
                        level.description, level.minRango, level.maxRango);

                dto.getLevelCounts().put(levelKey, count);
            });
        });

        return new ArrayList<>(result.values());
    }
    private static class LevelInfo {
        Long id;
        String description;
        Double minRango;
        Double maxRango;

        LevelInfo(Long id, String description, Double minRango, Double maxRango) {
            this.id = id;
            this.description = description;
            this.minRango = minRango;
            this.maxRango = maxRango;
        }
    }
}
