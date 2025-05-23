package unicauca.edu.co.sga.evaluation_service.application.services.stats;

import org.springframework.stereotype.Service;
import unicauca.edu.co.sga.evaluation_service.application.dto.response.stats.CriterionHistogramDTO;
import unicauca.edu.co.sga.evaluation_service.application.dto.response.stats.NivelDesempenoDTO;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.repositories.EvaluationRepository;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.repositories.stats.CriterioHistogramaRepository;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
//BORRAR
@Service
public class HistogramService {

    private final EvaluationRepository evaluationRepository;

    public HistogramService(EvaluationRepository evaluationRepository) {
        this.evaluationRepository = evaluationRepository;
    }


    /*public List<CriterionHistogramDTO> getHistogram(Long subjectId, Long courseId, Long rubricId, String period) {
        List<CriterioHistogramaRepository> resultados = evaluationRepository.getHistogramaPorCriterio(courseId, rubricId, subjectId, period);

        Map<Long, CriterionHistogramDTO> mapa = new LinkedHashMap<>();

        for (CriterioHistogramaRepository row : resultados) {
            Long id = row.getIdCriterio();
            CriterionHistogramDTO dto = mapa.computeIfAbsent(id, k ->
                    new CriterionHistogramDTO(k, row.getDescripcionCriterio())
            );
            dto.getNiveles().add(new NivelDesempenoDTO(row.getNivel(), row.getCantidad()));
        }

        return new ArrayList<>(mapa.values());
    }*/

}
