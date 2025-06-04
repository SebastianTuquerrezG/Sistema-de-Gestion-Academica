package unicauca.edu.co.sga.evaluation_service.application.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import unicauca.edu.co.sga.evaluation_service.application.dto.request.RARequestDTO;
import unicauca.edu.co.sga.evaluation_service.application.dto.response.RAResponseDTO;
import unicauca.edu.co.sga.evaluation_service.application.ports.RAPort;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.mappers.RAMapper;
import unicauca.edu.co.sga.evaluation_service.infrastructure.persistence.repositories.RARepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RAServive implements RAPort {

    private final RARepository raRepository;

    @Override
    public List<RAResponseDTO> getRAs() {
        return raRepository.findAllWithCourses().stream()
                .map(RAMapper::toModel)
                .map(RAMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<RAResponseDTO> getRAById(Long id) {
        return raRepository.findById(id)
                .map(RAMapper::toModel)
                .map(RAMapper::toDTO);
    }

    @Override
    public RAResponseDTO saveRA(RARequestDTO RA) {
        var ra = RAMapper.toModel(RA);
        var raEntity = RAMapper.toEntity(ra);
        return RAMapper.toDTO(
                RAMapper.toModel(
                        raRepository.save(raEntity)));
    }

    @Override
    public boolean deleteRA(Long id) {
        if (raRepository.existsById(id)) {
            raRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean updateRA(Long id, RARequestDTO RA) {
        if (raRepository.existsById(id)) {
            RA.setId(id);
            var ra = RAMapper.toModel(RA);
            var raEntity = RAMapper.toEntity(ra);
            raRepository.save(raEntity);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<RAResponseDTO> getRAsByName(String name) {
        return raRepository.findByNameContainingIgnoreCase(name).stream()
                .map(RAMapper::toModel)
                .map(RAMapper::toDTO)
                .toList();
    }
}