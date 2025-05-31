package unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.mappers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import unicauca.edu.co.sga.evaluation_service.application.dto.request.RubricRequestDTO;
import unicauca.edu.co.sga.evaluation_service.application.dto.response.RubricResponseDTO;
import unicauca.edu.co.sga.evaluation_service.domain.models.Rubric;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.entities.RAEntity;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.entities.RubricEntity;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.entities.SubjectEntity;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.repositories.RARepository;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.repositories.SubjectRepository;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class RubricMapper {

    private final SubjectRepository subjectRepository;
    private final RARepository raRepository;

    public static Rubric toModel(RubricRequestDTO dto){
        return Rubric.builder()
                .nombreRubrica(dto.getNombreRubrica())
                .objetivoEstudio(dto.getObjetivoEstudio())
                .notaRubrica(dto.getNotaRubrica())
                .ra(dto.getRaId())
//                .competence(dto.getCompetence())
                .subject(null)
                .criterios(null)
                .build();
    }

    public static RubricResponseDTO toDTO(Rubric model){
        return RubricResponseDTO.builder()
                .id(model.getIdRubrica())
                .name(model.getNombreRubrica())
                .ra_id(model.getRa())
                .raName(model.getRaName())
                .build();
    }

    public static Rubric toModel(RubricEntity entity){
        return Rubric.builder()
                .idRubrica(entity.getIdRubrica())
                .nombreRubrica(entity.getNombreRubrica())
                .objetivoEstudio(entity.getObjetivoEstudio())
                .notaRubrica(entity.getNotaRubrica())
                .ra(entity.getRa().getId())
                .raName(entity.getRa().getName())
//                .competence(entity.getCompetence())
                .subject(null)
                .evaluation(null)
                .criterios(null)
                .build();
    }

    public static RubricEntity toEntity(Rubric model){
        return RubricEntity.builder()
                .idRubrica(model.getIdRubrica())
                .nombreRubrica(model.getNombreRubrica())
                .estado(model.getStatus())
                .notaRubrica(model.getNotaRubrica())
                .objetivoEstudio(model.getObjetivoEstudio())
//                .competence(model.getCompetence())
                .subject(null)
                .criterios(null)
                .evaluation(null)
//                .updated_at(null)
//                .created_at(null)
                .build();
    }

    public RubricEntity toEntityRubric(RubricRequestDTO model){
        Optional<SubjectEntity> subject = subjectRepository.findById(model.getIdMateria());
        Optional<RAEntity> ra = raRepository.findById(model.getRaId());
        return RubricEntity.builder()
                .idRubrica(model.getIdRubrica())
                .nombreRubrica(model.getNombreRubrica())
                .estado(model.getEstado())
                .notaRubrica(model.getNotaRubrica())
                .objetivoEstudio(model.getObjetivoEstudio())
//                .competence(model.getCompetence())
                .subject(subject.orElse(null))
                .criterios(model.getCriterios())
                .ra(ra.orElse(null))
                .evaluation(null)
//                .updated_at(null)
//                .created_at(null)
                .build();
    }
}
