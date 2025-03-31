package org.unicauca.modulorubricacriterio.Fachada.service;



import java.util.List;

import org.unicauca.modulorubricacriterio.Fachada.dto.CriterioDTO;

public interface ICriterioService {
    public List<CriterioDTO> findAll();
    public CriterioDTO findById(Long id);
    public CriterioDTO save(CriterioDTO criterioDTO);
    public CriterioDTO update(Long id,CriterioDTO criterioDTO);
    public boolean delete(Long id);
}
