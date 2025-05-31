package unicauca.edu.co.sga.evaluation_service.application.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import unicauca.edu.co.sga.evaluation_service.application.dto.request.RubricRequestDTO;
import unicauca.edu.co.sga.evaluation_service.application.dto.response.RubricResponseDTO;
import unicauca.edu.co.sga.evaluation_service.application.ports.RubricPort;
import unicauca.edu.co.sga.evaluation_service.domain.enums.GeneralEnums;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.entities.RubricEntity;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.mappers.RubricMapper;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.repositories.RubricRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RubricService implements RubricPort {

    private final RubricRepository rubricRepository;
    private final RubricMapper rubricMapper;

    @Override
    public List<RubricResponseDTO> getRubrics() {
        return List.of();
    }

    @Override
    public Optional<RubricPort> getRubricById(Long id) {
        return Optional.empty();
    }

    @Override
    public RubricEntity saveRubric(RubricRequestDTO rubric) {
        rubric.setEstado(GeneralEnums.status.ACTIVO);
        RubricEntity objRubricaEntity =  rubricMapper.toEntityRubric(rubric);
        rubricRepository.save(objRubricaEntity);
        return objRubricaEntity;
    }

    @Override
    public boolean deleteRubric(Long id) {
        Optional<RubricEntity> rubric = rubricRepository.findById(id);
        if (rubric.isPresent()){
            rubricRepository.deleteById(id);
        }
        return false;
    }

    @Override
    public boolean updateRubric(Long id, RubricRequestDTO rubric) {
        return false;
    }

    @Override
    public RubricEntity updateRubricRabbit(RubricRequestDTO rubric) {
        RubricEntity objRubricaEntity =  rubricMapper.toEntityRubric(rubric);
        rubricRepository.save(objRubricaEntity);
        return objRubricaEntity;
    }

    @Override
    public Optional<RubricResponseDTO> getRubricByName(String name) {
        return Optional.empty();
    }

    @Override
    public List<RubricResponseDTO> getRubricsBySubject(Long id) {
        return rubricRepository.findBySubjectId(id).stream()
                .map(RubricMapper::toModel)
                .map(RubricMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<RubricResponseDTO> getRubricsByRA(Long id) {
        return List.of();
    }

    @Override
    public List<RubricResponseDTO> getRubricsByCriteria(Long id) {
        return List.of();
    }

    @Override
    public List<RubricResponseDTO> getRubricsByStatus(GeneralEnums.status status) {
        return List.of();
    }
}
