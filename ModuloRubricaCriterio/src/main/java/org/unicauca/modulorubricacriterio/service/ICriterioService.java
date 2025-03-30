package org.unicauca.modulorubricacriterio.service;

import org.unicauca.modulorubricacriterio.dto.CriterioDTO;
import org.unicauca.modulorubricacriterio.dto.RubricaDTO;
import org.unicauca.modulorubricacriterio.model.CriterioEntity;

import java.util.List;

public interface ICriterioService {
    public List<CriterioDTO> findAll();
    public CriterioDTO findById(Long id);
    public CriterioDTO save(CriterioDTO criterioDTO);
    public CriterioDTO update(Long id,CriterioDTO criterioDTO);
    public boolean delete(Long id);
}
