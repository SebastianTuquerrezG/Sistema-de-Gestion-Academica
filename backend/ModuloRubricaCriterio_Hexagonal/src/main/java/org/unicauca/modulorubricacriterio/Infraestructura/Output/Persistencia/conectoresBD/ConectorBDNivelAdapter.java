package org.unicauca.modulorubricacriterio.Infraestructura.Output.Persistencia.conectoresBD;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;
import org.unicauca.modulorubricacriterio.Aplicacion.Output.IConectorBDNivelPort;
import org.unicauca.modulorubricacriterio.Dominio.Modelos.Nivel;
import org.unicauca.modulorubricacriterio.Infraestructura.Fachada.exception.exceptionOwn.EntidadNoExisteException;
import org.unicauca.modulorubricacriterio.Infraestructura.Fachada.exception.exceptionOwn.ReglaNegocioException;
import org.unicauca.modulorubricacriterio.Infraestructura.Output.Persistencia.entity.CriterioEntity;
import org.unicauca.modulorubricacriterio.Infraestructura.Output.Persistencia.entity.NivelEntity;
import org.unicauca.modulorubricacriterio.Infraestructura.Output.Persistencia.repository.CriterioRepository;
import org.unicauca.modulorubricacriterio.Infraestructura.Output.Persistencia.repository.NivelRepository;

@Service
public class ConectorBDNivelAdapter implements IConectorBDNivelPort{

    private final CriterioRepository criterioRepository;
    private final NivelRepository nivelRepository;
    private final ModelMapper nivelMapper;

    public ConectorBDNivelAdapter(CriterioRepository objCriterioRepository, NivelRepository nivelRepository, ModelMapper nivelMapper) {
        this.criterioRepository = objCriterioRepository; 
        this.nivelRepository = nivelRepository;
        this.nivelMapper = nivelMapper;
    }

    @Override
    public List<Nivel> findAll() {
        List<NivelEntity> listNivelEntity = this.nivelRepository.findAll();
        List<Nivel> listNivel = this.nivelMapper.map(listNivelEntity, new TypeToken<List<Nivel>>() {}.getType());
        for (Nivel level : listNivel) {
            level.setIdCriterio(level.getCriterio().getIdCriterio());
        }
        return listNivel;
    }

    @Override
    public Nivel findById(Long id) {
        NivelEntity objNivelEntity = this.nivelRepository.findById(id).orElse(null);
        if (objNivelEntity == null) {
            throw new EntidadNoExisteException("No existe una entidad asociada al id{"+id+"}");
        }
        Nivel objNivel = this.nivelMapper.map(objNivelEntity, Nivel.class);
        objNivel.setIdCriterio(objNivel.getCriterio().getIdCriterio());
        return objNivel;
    }

    @Override
    public Nivel save(Nivel objNivel) {
        NivelEntity objNivelEntity = this.nivelMapper.map(objNivel, NivelEntity.class);
        if(objNivel.getIdCriterio()==null)
        {
            throw new ReglaNegocioException("El id del criterio asociado al criterio NO puede ser nulo");
        }

        CriterioEntity objCriterioAsociado = this.criterioRepository.findById(objNivel.getIdCriterio()).orElse(null);
        
        if(objCriterioAsociado == null)
        {
            throw new EntidadNoExisteException("El criterio no existe");
        }   

        objNivelEntity.setCriterio(objCriterioAsociado);
        NivelEntity objNivelEntityGuardado = this.nivelRepository.save(objNivelEntity);
        Nivel objNivelGuardado = this.nivelMapper.map(objNivelEntityGuardado, Nivel.class);
        objNivelGuardado.setIdCriterio(objNivelGuardado.getCriterio().getIdCriterio());
        return objNivelGuardado;
    }

    @Override
    public Nivel update(Long id, Nivel objNivel) {
        NivelEntity objNivelActualizado = null;
        NivelEntity objNivelActual = this.nivelRepository.findById(id).orElse(null);
        if(objNivelActual == null)
        {
            throw new EntidadNoExisteException("Nivel con el id {"+id+"} no existe");
        }
        objNivelActual.setCriterio(this.criterioRepository.findById(objNivel.getIdCriterio()).orElse(null));
        objNivelActual.setNivelDescripcion(objNivel.getNivelDescripcion());
        objNivelActual.setRangoNota(objNivel.getRangoNota());
        objNivelActualizado = this.nivelRepository.save(objNivelActual);
        Nivel objNivelRetorno = this.nivelMapper.map(objNivelActualizado, Nivel.class);
        objNivelRetorno.setIdCriterio(objNivelRetorno.getCriterio().getIdCriterio());
        return objNivelRetorno;
    }

    @Override
    public Nivel delete(Long idNivel) {
        NivelEntity objNivelEliminar = this.nivelRepository.findById(idNivel).orElse(null);
        if(objNivelEliminar == null)
        {
            throw new EntidadNoExisteException("Nivel con el id {"+idNivel+"} no existe"); 
        }
        
        Nivel objNivelRetorno = this.nivelMapper.map(objNivelEliminar, Nivel.class);
        objNivelRetorno.setIdCriterio(objNivelRetorno.getCriterio().getIdCriterio());
        this.nivelRepository.delete(objNivelEliminar);
        return objNivelRetorno;
    }

}
