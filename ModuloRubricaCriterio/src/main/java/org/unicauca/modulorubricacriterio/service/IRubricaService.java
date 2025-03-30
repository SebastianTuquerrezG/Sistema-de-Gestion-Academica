package org.unicauca.modulorubricacriterio.service;

import org.unicauca.modulorubricacriterio.dto.RubricaDTO;
import org.unicauca.modulorubricacriterio.model.RubricaEntity;

import java.util.List;

public interface IRubricaService {
    public List<RubricaDTO> findAll();
    public RubricaDTO findById(Long id);
    public RubricaDTO save(RubricaDTO rubricaDTO);
    public RubricaDTO update(Long id,RubricaDTO rubricaDTO);
    public boolean delete(Long id);
}
