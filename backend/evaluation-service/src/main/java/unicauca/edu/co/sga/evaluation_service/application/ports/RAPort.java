package unicauca.edu.co.sga.evaluation_service.application.ports;

import unicauca.edu.co.sga.evaluation_service.application.dto.request.RARequestDTO;
import unicauca.edu.co.sga.evaluation_service.application.dto.response.RAResponseDTO;

import java.util.List;
import java.util.Optional;

public interface RAPort {
    List<RAResponseDTO> getRAs();
    Optional<RAResponseDTO> getRAById(Long id);
    RAResponseDTO saveRA(RARequestDTO RA);
    boolean deleteRA(Long id);
    boolean updateRA(Long id, RARequestDTO RA);
    List<RAResponseDTO> getRAsByName(String name);
}
