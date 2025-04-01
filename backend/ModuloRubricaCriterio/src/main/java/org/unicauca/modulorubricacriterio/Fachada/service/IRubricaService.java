package org.unicauca.modulorubricacriterio.Fachada.service;


import java.util.List;

import org.unicauca.modulorubricacriterio.Fachada.dto.RubricaDTO;

public interface IRubricaService {
    public List<RubricaDTO> findAll();
    public RubricaDTO findById(Long id);
    public RubricaDTO save(RubricaDTO rubricaDTO);
    public RubricaDTO update(Long id,RubricaDTO rubricaDTO);
    public RubricaDTO delete(Long id, RubricaDTO estado);
}
