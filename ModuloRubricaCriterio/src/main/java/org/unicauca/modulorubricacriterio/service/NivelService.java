package org.unicauca.modulorubricacriterio.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.unicauca.modulorubricacriterio.dto.NivelDTO;
import org.unicauca.modulorubricacriterio.exception.exceptionOwn.EntidadNoExisteException;
import org.unicauca.modulorubricacriterio.exception.exceptionOwn.ReglaNegocioException;
import org.unicauca.modulorubricacriterio.model.CriterioEntity;
import org.unicauca.modulorubricacriterio.model.NivelEntity;
import org.unicauca.modulorubricacriterio.repository.CriterioRepository;
import org.unicauca.modulorubricacriterio.repository.NivelRepository;

import java.util.List;

@Service
public class NivelService implements INivelService{

    private final CriterioRepository criterioRepository;
    private ModelMapper modelMapper;
    private NivelRepository nivelRepository;

    public NivelService(ModelMapper modelMapper, NivelRepository nivelRepository, CriterioRepository criterioRepository) {
        this.modelMapper = modelMapper;
        this.nivelRepository = nivelRepository;
        this.criterioRepository = criterioRepository;
    }

    @Override
    public List<NivelDTO> findAll() {
        List<NivelEntity> niveles= nivelRepository.findAll();
        List<NivelDTO>nivelesDTO=modelMapper.map(niveles,List.class);
        return nivelesDTO;
    }

    @Override
    public NivelDTO findById(Long id) {

        NivelEntity nivel = nivelRepository.findById(id).orElse(null);
        if(nivel == null) {
            throw new EntidadNoExisteException("No existe el nivel a buscar.");
        }
        NivelDTO nivelDTO = this.modelMapper.map(nivel, NivelDTO.class);
        nivelDTO.setId_criterio(nivel.getCriterio().getIdCriterio());

        return nivelDTO;
    }

    @Override
    public NivelDTO save(NivelDTO nivelDTO) {

        NivelEntity nivelEntity = modelMapper.map(nivelDTO, NivelEntity.class);
        if(nivelDTO.getId_criterio() == null) {
            throw new ReglaNegocioException("El id de el criterio no puede ser nulo.");
        }

        CriterioEntity criterioEntity = criterioRepository.findById(nivelDTO.getId_criterio()).orElse(null);
        if(criterioEntity == null) {
            throw new EntidadNoExisteException("No existe el criterio a buscar.");
        }

        nivelEntity.setCriterio(criterioEntity);
        nivelEntity = nivelRepository.save(nivelEntity);
        NivelDTO nivelDTO1 = modelMapper.map(nivelEntity, NivelDTO.class);
        nivelDTO1.setId_criterio(nivelEntity.getCriterio().getIdCriterio());


        return nivelDTO1;
    }

    @Override
    public NivelDTO update(Long id, NivelDTO nivel) {
        NivelDTO nivelDTO = null;
        NivelEntity nivelEntity = nivelRepository.findById(id).orElse(null);
        if(nivelEntity == null) {
            throw new EntidadNoExisteException("No existe el nivel a editar.");
        }
        nivelEntity.setCriterio(criterioRepository.findById(nivel.getId_criterio()).orElse(null));
        nivelEntity.setNivelDescripcion(nivel.getNivelDescripcion());
        nivelEntity.setRangoNota(nivel.getRangoNota());
        nivelEntity = nivelRepository.save(nivelEntity);
        nivelDTO = modelMapper.map(nivelEntity, NivelDTO.class);
        nivelDTO.setId_criterio(nivelEntity.getCriterio().getIdCriterio());

        return nivelDTO;
    }

    @Override
    public boolean delete(Long id) {

        boolean bandera = false;
        if(nivelRepository.existsById(id)) {
            nivelRepository.deleteById(id);
            bandera = true;
        }else{
            throw new EntidadNoExisteException("No existe el nivel a eliminar.");
        }

        return false;
    }
}
