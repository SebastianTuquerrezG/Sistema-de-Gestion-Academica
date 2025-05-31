package unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.mappers;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import unicauca.edu.co.sga.evaluation_service.application.dto.request.CriteriaRequestDTO;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.entities.CriteriaEntity;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.entities.RubricEntity;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.repositories.RubricRepository;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CriteriaMapper {

    private final RubricRepository rubricRepository;

    public CriteriaEntity toEntity(CriteriaRequestDTO dto){
        Optional<RubricEntity> rubric = rubricRepository.findById(dto.getIdRubrica());
        return CriteriaEntity.builder()
                .idCriterio(dto.getIdCriterio())
                .crfComentario(dto.getCrfComentario())
                .crfDescripcion(dto.getCrfDescripcion())
                .crfNota(dto.getCrfNota())
                .niveles(dto.getNiveles())
                .rubric(rubric.orElse(null))
                .calificaciones(null)
                .build();
    }
}
