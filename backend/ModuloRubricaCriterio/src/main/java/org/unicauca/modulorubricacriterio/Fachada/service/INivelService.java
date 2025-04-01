package org.unicauca.modulorubricacriterio.Fachada.service;


import java.util.List;

import org.unicauca.modulorubricacriterio.Fachada.dto.NivelDTO;

public interface INivelService {
    public List<NivelDTO> findAll();
    public NivelDTO findById(Long id);
    public NivelDTO save(NivelDTO nivelDTO);
    public NivelDTO update(Long id,NivelDTO nivelDTO);
    public boolean delete(Long id);
}
