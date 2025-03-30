package org.unicauca.modulorubricacriterio.service;

import org.unicauca.modulorubricacriterio.dto.CriterioDTO;
import org.unicauca.modulorubricacriterio.dto.NivelDTO;

import java.util.List;

public interface INivelService {
    public List<NivelDTO> findAll();
    public NivelDTO findById(Long id);
    public NivelDTO save(NivelDTO nivelDTO);
    public NivelDTO update(Long id,NivelDTO nivelDTO);
    public boolean delete(Long id);
}
