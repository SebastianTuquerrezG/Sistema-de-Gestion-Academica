package unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.mappers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import unicauca.edu.co.sga.evaluation_service.application.dto.request.RubricRequestDTO;
import unicauca.edu.co.sga.evaluation_service.application.dto.response.RubricResponseDTO;
import unicauca.edu.co.sga.evaluation_service.domain.models.Rubric;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.entities.*;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.repositories.CriteriaRepository;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.repositories.RARepository;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.repositories.SubjectRepository;

import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class RubricMapper {

    private final SubjectRepository subjectRepository;
    private final RARepository raRepository;
    private final CriteriaRepository criteriaRepository;

    public static Rubric toModel(RubricRequestDTO dto){
        return Rubric.builder()
                .nombreRubrica(dto.getNombreRubrica())
                .objetivoEstudio(dto.getObjetivoEstudio())
                .notaRubrica(dto.getNotaRubrica())
                .ra(dto.getRaId())
//                .competence(dto.getCompetence())
                .subject(Collections.singleton(dto.getIdMateria()))
                .criterios(dto.getCriterios()!= null ? dto.getCriterios()
                        .stream().map(CriteriaEntity::getIdCriterio).collect(Collectors.toSet()) : Collections.emptySet())
                .status(dto.getEstado())
                .evaluation(null)
                .build();
    }

    public RubricResponseDTO toDTO(Rubric model){
        Optional<RAEntity> raEntity = raRepository.findById(model.getRa());
        assert raEntity.orElse(null) != null;
        return RubricResponseDTO.builder()
                .id(model.getIdRubrica())
                .name(model.getNombreRubrica())
                .studyObjective(model.getObjetivoEstudio())
                .rubricScore(model.getNotaRubrica())
                .ra_id(model.getRa())
                .raName(raEntity.orElse(null).getName())
                .status(model.getStatus())
                .build();
    }

    public static Rubric toModel(RubricEntity entity){
        return Rubric.builder()
                .idRubrica(entity.getIdRubrica())
                .nombreRubrica(entity.getNombreRubrica())
                .objetivoEstudio(entity.getObjetivoEstudio())
                .notaRubrica(entity.getNotaRubrica())
                .ra(entity.getRa().getId())
                .subject(entity.getSubject() != null ?
                        Collections.singleton(entity.getSubject().getId()) : Collections.emptySet())
                .evaluation(entity.getEvaluation() != null ?
                        entity.getEvaluation().stream().map(EvaluationEntity::getId).collect(Collectors.toSet()) : Collections.emptySet())
                .criterios(entity.getCriterios() != null ?
                        entity.getCriterios().stream().map(CriteriaEntity::getIdCriterio).collect(Collectors.toSet()) : Collections.emptySet())
                .build();
    }

    public static RubricEntity toEntity(Rubric model){
        return RubricEntity.builder()
                .idRubrica(model.getIdRubrica())
                .nombreRubrica(model.getNombreRubrica())
                .estado(model.getStatus())
                .notaRubrica(model.getNotaRubrica())
                .objetivoEstudio(model.getObjetivoEstudio())
                .subject(model.getSubject() != null && !model.getSubject().isEmpty() ?
                        SubjectEntity.builder().id(model.getSubject().iterator().next()).build() : null)
                .criterios(model.getCriterios().stream()
                        .map(id -> CriteriaEntity.builder().idCriterio(id).build())
                        .toList())
                .evaluation(model.getEvaluation() != null && !model.getEvaluation().isEmpty() ?
                        model.getEvaluation().stream()
                                .map(id -> EvaluationEntity.builder().id(id).build())
                                .collect(Collectors.toSet()) : null)
                .ra(model.getRa() != null ? RAEntity.builder().id(model.getRa()).build() : null)
                .build();
    }
}
