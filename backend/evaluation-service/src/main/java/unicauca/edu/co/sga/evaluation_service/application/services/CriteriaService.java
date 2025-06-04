package unicauca.edu.co.sga.evaluation_service.application.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import unicauca.edu.co.sga.evaluation_service.application.dto.request.CriteriaRequestDTO;
import unicauca.edu.co.sga.evaluation_service.application.dto.response.CriteriaResponseDTO;
import unicauca.edu.co.sga.evaluation_service.application.ports.CriteriaPort;
import unicauca.edu.co.sga.evaluation_service.domain.models.Criteria;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.entities.CriteriaEntity;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.entities.RubricEntity;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.mappers.CriteriaMapper;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.repositories.CriteriaRepository;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.repositories.RubricRepository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CriteriaService implements CriteriaPort {

    private final CriteriaMapper criteriaMapper;
    private final CriteriaRepository criteriaRepository;
    private final RubricRepository rubricRepository;

    @Override
    public List<CriteriaResponseDTO> getCriteria() {
        return criteriaRepository.findAll().stream()
                .map(CriteriaMapper::toModel)
                .map(criteriaMapper::toDTO)
                .toList();
    }

    @Override
    public Optional<CriteriaResponseDTO> getCriteriaById(Long id) {
        return criteriaRepository.findById(id)
                .map(CriteriaMapper::toModel)
                .map(criteriaMapper::toDTO);
    }

    @Override
    public CriteriaResponseDTO  saveCriteria(CriteriaRequestDTO criteria) {
        Criteria criteriaModel = CriteriaMapper.toModel(criteria);
        CriteriaEntity criteriaEntity = criteriaMapper.toEntity(criteriaModel);
        return criteriaMapper.toDTO(
                CriteriaMapper.toModel(
                        criteriaRepository.save(criteriaEntity))
        );
    }

    @Override
    public boolean deleteCriteria(Long id) {
        if (criteriaRepository.existsById(id)) {
            criteriaRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean updateCriteria(Long id, CriteriaRequestDTO criteria) {
        Optional<CriteriaEntity> criteriaModel = criteriaRepository.findById(id);
        if (criteriaModel.isPresent()) {
            CriteriaEntity criteriaEntity = criteriaModel.get();
            Optional<RubricEntity> rubricEntity = rubricRepository.findById(criteria.getIdRubrica());
            criteriaEntity.setCrfComentario(criteria.getCrfComentario());
            criteriaEntity.setCrfDescripcion(criteria.getCrfDescripcion());
            criteriaEntity.setCrfPorcentaje(criteria.getCrfPorcentaje());
            criteriaEntity.setCrfNota(criteria.getCrfNota());
            criteriaEntity.setRubric(rubricEntity.orElseThrow(() -> new RuntimeException("Rubric not found")));
            criteriaEntity.setNiveles(criteria.getNiveles());
            criteriaRepository.save(criteriaEntity);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<CriteriaResponseDTO> getCriteriaByRubric(Long id) {
        return criteriaRepository.findByRubric(rubricRepository.findById(id)).stream()
                .map(CriteriaMapper::toModel)
                .map(criteriaMapper::toDTO)
                .toList();
    }
}
