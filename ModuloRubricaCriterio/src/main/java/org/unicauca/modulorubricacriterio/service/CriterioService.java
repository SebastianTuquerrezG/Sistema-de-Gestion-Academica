package org.unicauca.modulorubricacriterio.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.unicauca.modulorubricacriterio.ModuloRubricaCriterioApplication;
import org.unicauca.modulorubricacriterio.dto.CriterioDTO;
import org.unicauca.modulorubricacriterio.dto.RubricaDTO;
import org.unicauca.modulorubricacriterio.exception.exceptionOwn.EntidadNoExisteException;
import org.unicauca.modulorubricacriterio.exception.exceptionOwn.ReglaNegocioException;
import org.unicauca.modulorubricacriterio.model.CriterioEntity;
import org.unicauca.modulorubricacriterio.model.RubricaEntity;
import org.unicauca.modulorubricacriterio.repository.CriterioRepository;
import org.unicauca.modulorubricacriterio.repository.RubricaRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class CriterioService implements ICriterioService{

    private final ModuloRubricaCriterioApplication moduloRubricaCriterioApplication;
    private CriterioRepository criterioRepository;
    private ModelMapper modelMapper;
    private RubricaRepository rubricaRepository;

    public CriterioService(CriterioRepository criterioRepository, ModelMapper modelMapper, RubricaRepository rubricaRepository, ModuloRubricaCriterioApplication moduloRubricaCriterioApplication) {
        this.criterioRepository = criterioRepository;
        this.modelMapper = modelMapper;
        this.rubricaRepository = rubricaRepository;
        this.moduloRubricaCriterioApplication = moduloRubricaCriterioApplication;
    }

    @Override
    public List<CriterioDTO> findAll() {
        List<CriterioEntity>criterios = criterioRepository.findAll();
        List<CriterioDTO> criterioDTO = this.modelMapper.map(criterios, List.class);
        return criterioDTO;
    }

    @Override
    public CriterioDTO findById(Long id) {

        CriterioEntity criterio = criterioRepository.findById(id).orElse(null);
        if(criterio == null) {
            EntidadNoExisteException objException = new EntidadNoExisteException("Criterio no encontrado");
            throw objException;
        }
        CriterioDTO criterioDTO = this.modelMapper.map(criterio, CriterioDTO.class);
        criterioDTO.setRubrica_id(criterio.getRubrica().getIdRubrica());

        return criterioDTO;
    }

    @Override
    public CriterioDTO save(CriterioDTO criterioDTO) {
        //Mapeando el DTO
        CriterioEntity criterioEntity = modelMapper.map(criterioDTO, CriterioEntity.class);

        if(criterioDTO.getRubrica_id()==null) {
            throw new ReglaNegocioException("El id de la rubrica asociado al criterio NO puede ser nulo");
        }

        RubricaEntity rubrica= rubricaRepository.findById(criterioDTO.getRubrica_id()).orElse(null);
        if(rubrica==null) {
            throw new EntidadNoExisteException("La rúbrica no existe");
        }
        criterioEntity.setRubrica(rubricaRepository.findById(criterioDTO.getRubrica_id()).orElse(null));
        criterioEntity = criterioRepository.save(criterioEntity);
        CriterioDTO criterioDTO1 = modelMapper.map(criterioEntity,CriterioDTO.class);
        criterioDTO1.setRubrica_id(criterioEntity.getRubrica().getIdRubrica());
        return criterioDTO1;
    }



    @Override
    public CriterioDTO update(Long id, CriterioDTO criterio) {
        CriterioDTO criterioDTO = null;
        CriterioEntity criterioEntity = criterioRepository.findById(id).orElse(null);

        if(criterioEntity==null) {
            throw new EntidadNoExisteException("Criterio no encontrado");
        }
        criterioEntity.setRubrica(rubricaRepository.findById(criterio.getRubrica_id()).orElse(null));
        criterioEntity.setCrfDescripcion(criterio.getCrfDescripcion());
        criterioEntity.setCrfPorcentaje(criterio.getCrfPorcentaje());
        criterioEntity.setCrfNota(criterio.getCrfNota());
        criterioEntity.setCrfComentario(criterio.getCrfComentario());
        criterioEntity = criterioRepository.save(criterioEntity);

        criterioDTO = modelMapper.map(criterioEntity,CriterioDTO.class);
        criterioDTO.setRubrica_id(criterioEntity.getRubrica().getIdRubrica());

        return criterioDTO;
    }

    @Override
    public boolean delete(Long id) {
        boolean bandera = false;

        if(criterioRepository.existsById(id)) {
            criterioRepository.deleteById(id);
            bandera = true;
        }else{
            throw new EntidadNoExisteException("Criterio no encontrado");
        }

        return bandera;
    }

}
