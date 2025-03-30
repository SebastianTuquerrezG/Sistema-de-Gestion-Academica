package org.unicauca.modulorubricacriterio.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.unicauca.modulorubricacriterio.dto.RubricaDTO;
import org.unicauca.modulorubricacriterio.exception.exceptionOwn.EntidadNoExisteException;
import org.unicauca.modulorubricacriterio.model.RubricaEntity;
import org.unicauca.modulorubricacriterio.repository.RubricaRepository;

import java.util.List;

@Service
public class RubricaService implements IRubricaService{

    private RubricaRepository rbRepository;
    private ModelMapper modelMapper;

    public RubricaService(RubricaRepository rbRepository, ModelMapper modelMapper) {
        this.rbRepository = rbRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<RubricaDTO> findAll() {
        List<RubricaEntity>rubricas = rbRepository.findAll();
        List<RubricaDTO> rubricasDTO = this.modelMapper.map(rubricas, List.class);
        return rubricasDTO;
    }

    @Override
    public RubricaDTO findById(Long id) {

        RubricaEntity rubrica = rbRepository.findById(id).orElse(null);
        if(rubrica == null) {
            EntidadNoExisteException objException = new EntidadNoExisteException("No existe ninguna rubrica con ese ID.");
            throw objException;
        }
        return modelMapper.map(rubrica, RubricaDTO.class);
    }

    @Override
    public RubricaDTO save(RubricaDTO rubricaDTO) {
        RubricaEntity rubrica = modelMapper.map(rubricaDTO, RubricaEntity.class);
        rubrica = rbRepository.save(rubrica);
        rubricaDTO = modelMapper.map(rubrica, RubricaDTO.class);
        return rubricaDTO;
    }

    public RubricaDTO update(Long id, RubricaDTO rubrica) {
        RubricaDTO rubricaDTO = null;

        RubricaEntity rubricaEntity = rbRepository.findById(id).orElse(null);
        if(rubricaEntity == null) {
            throw new EntidadNoExisteException("No existe ninguna rubrica con ese ID.");
        }

        System.out.println("id recibido: " + rubricaEntity.getIdRubrica());

        //Actualizando datos
        rubricaEntity.setNombreRubrica(rubrica.getNombreRubrica());
        rubricaEntity.setNotaRubrica(rubrica.getNotaRubrica());
        rubricaEntity.setObjetivoEstudio(rubrica.getObjetivoEstudio());

        // Guardamos la entidad actualizada
        rbRepository.save(rubricaEntity);

        // Mapeamos la entidad actualizada de nuevo a DTO
        rubricaDTO = this.modelMapper.map(rubricaEntity, RubricaDTO.class);

        return rubricaDTO;
    }


    @Override
    public boolean delete(Long id) {
        boolean bandera = false;
        if(!rbRepository.existsById(id)) {
            throw new EntidadNoExisteException("No existe ninguna rubrica con ese ID.");
        }
        rbRepository.deleteById(id);
        bandera = true;


        return bandera;
    }



}
