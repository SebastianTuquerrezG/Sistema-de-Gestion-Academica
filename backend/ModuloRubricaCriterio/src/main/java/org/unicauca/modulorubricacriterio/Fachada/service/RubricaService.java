package org.unicauca.modulorubricacriterio.Fachada.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.unicauca.modulorubricacriterio.AccesoADatos.model.RubricaEntity;
import org.unicauca.modulorubricacriterio.AccesoADatos.repository.RubricaRepository;
import org.unicauca.modulorubricacriterio.Fachada.dto.RubricaDTO;
import org.unicauca.modulorubricacriterio.Fachada.exception.exceptionOwn.CambioInvalidoException;
import org.unicauca.modulorubricacriterio.Fachada.exception.exceptionOwn.EntidadNoExisteException;
import org.unicauca.modulorubricacriterio.Fachada.validacionEstados.EstadosController;
import org.unicauca.modulorubricacriterio.Fachada.validacionEstados.EstadosEnum;

import java.util.ArrayList;
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
        List<RubricaDTO> listaRubricas = new ArrayList<>();
        RubricaDTO rubricaDTO = null;

        if(rubricas.isEmpty()) {
            throw new EntidadNoExisteException("No existen rubricas en la base de datos.");
        }else
        {
            for (RubricaEntity rubricaEntity : rubricas) {
                rubricaDTO = this.modelMapper.map(rubricaEntity, RubricaDTO.class);
                rubricaDTO.setEstado(rubricaEntity.getEstadoRubrica().toString()); //mapear el estado manualmente
                rubricaDTO.setRubricaId("IS10"+rubricaEntity.getIdRubrica().toString()); // Asignar el ID generado a la DTO
                listaRubricas.add(rubricaDTO);
            }
        }
        
        
        return listaRubricas;
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
        rubrica.setEstadoRubrica(EstadosEnum.ACTIVO); // Estado por defecto al crear una nueva rubrica
        rubrica = rbRepository.save(rubrica);
        rubricaDTO = modelMapper.map(rubrica, RubricaDTO.class);
        rubricaDTO.setEstado(rubrica.getEstadoRubrica().toString()); //mapear el estado manualmente
        rubricaDTO.setRubricaId("IS10"+rubrica.getIdRubrica().toString()); // Asignar el ID generado a la DTO

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
        rubricaEntity.setEstadoRubrica(validarEstado(rubrica.getEstado()));

        // Guardamos la entidad actualizada
        rbRepository.save(rubricaEntity);

        // Mapeamos la entidad actualizada de nuevo a DTO
        rubricaDTO = this.modelMapper.map(rubricaEntity, RubricaDTO.class);
        rubricaDTO.setEstado(rubricaEntity.getEstadoRubrica().toString()); //mapear el estado manualmente
        rubricaDTO.setRubricaId("IS10"+rubricaEntity.getIdRubrica().toString()); // Asignar el ID generado a la DTO

        return rubricaDTO;
    }


    @Override
    public RubricaDTO delete(Long id, RubricaDTO estado) {
        RubricaDTO objRubricaDTO = null;
        EstadosEnum estadoSiguiente = validarEstado(estado.getEstado());
        RubricaEntity objRubrica = rbRepository.findById(id).orElse(null);
        if (objRubrica == null) {
            throw new EntidadNoExisteException("No existe ninguna rubrica con ese ID.");
        }

        EstadosController objEstadoActual = new EstadosController(objRubrica.getEstadoRubrica().ordinal());

        if(objEstadoActual.cambioEstado(estado.getEstado()).cambioPermitido())
        {
            objRubrica.setEstadoRubrica(estadoSiguiente);
            rbRepository.save(objRubrica);
            objRubricaDTO = this.modelMapper.map(objRubrica, RubricaDTO.class);
            objRubricaDTO.setEstado(objRubrica.getEstadoRubrica().toString()); //mapear el estado manualmente
            objRubricaDTO.setRubricaId("IS10"+objRubrica.getIdRubrica().toString()); // Asignar el ID generado a la DTO
        }else{
            throw new CambioInvalidoException(objEstadoActual.cambioEstado(estado.getEstado()).mensaje());
        }
        return objRubricaDTO;
    }

    private EstadosEnum validarEstado(String Estado)
    {
        EstadosEnum estadoSiguiente = null;
        switch (Estado) {
            case "ACTIVAR":
                estadoSiguiente = EstadosEnum.ACTIVO;
                break;
            case "DESACTIVAR":
                estadoSiguiente = EstadosEnum.INACTIVO;
                break;
        }
        return estadoSiguiente;
    }
    
}
