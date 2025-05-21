package org.unicauca.modulorubricacriterio.Infraestructura.Output.Persistencia.conectoresBD;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;
import org.unicauca.modulorubricacriterio.Aplicacion.Output.IConectorBDCriterioPort;
import org.unicauca.modulorubricacriterio.Dominio.Modelos.Criterio;
import org.unicauca.modulorubricacriterio.Infraestructura.Fachada.exception.exceptionOwn.EntidadNoExisteException;
import org.unicauca.modulorubricacriterio.Infraestructura.Fachada.exception.exceptionOwn.ReglaNegocioException;
import org.unicauca.modulorubricacriterio.Infraestructura.Output.Persistencia.entity.CriterioEntity;
import org.unicauca.modulorubricacriterio.Infraestructura.Output.Persistencia.entity.RubricaEntity;
import org.unicauca.modulorubricacriterio.Infraestructura.Output.Persistencia.repository.CriterioRepository;
import org.unicauca.modulorubricacriterio.Infraestructura.Output.Persistencia.repository.RubricaRepository;

@Service
public class ConectorBDCriterioAdapter implements IConectorBDCriterioPort{

    private final RubricaRepository rubricaRepository;
    private final CriterioRepository criterioRepository;
    private final ModelMapper criterioMapper;

    public ConectorBDCriterioAdapter(RubricaRepository objRubricaRepository,CriterioRepository criterioRepository, ModelMapper criterioMapper) {
        this.rubricaRepository = objRubricaRepository;
        this.criterioRepository = criterioRepository;
        this.criterioMapper = criterioMapper;
    }

    @Override
    public List<Criterio> findAll() {
        List<CriterioEntity> criterioEntities = this.criterioRepository.findAll();
        List<Criterio> listCriterio = this.criterioMapper.map(criterioEntities, new TypeToken<List<Criterio>>() {}.getType());
        for (Criterio criterio : listCriterio) {
            criterio.setIdRubrica(criterio.getRubrica().getIdRubrica());
        }
        return listCriterio;
    }

    @Override
    public Criterio findById(Long idCriterio) {
        CriterioEntity objCriterioEntity = this.criterioRepository.findById(idCriterio).orElse(null);
        if (objCriterioEntity == null) {
            throw new EntidadNoExisteException("No existe una entidad asociada al id{"+idCriterio+"}");
        }
        Criterio objCriterio = this.criterioMapper.map(objCriterioEntity, Criterio.class);
        objCriterio.setIdRubrica(objCriterio.getIdRubrica());
        return objCriterio;
    }

    @Override
    public Criterio save(Criterio objCriterio) {

        CriterioEntity objCriterioEntity = this.criterioMapper.map(objCriterio, CriterioEntity.class);
        if (objCriterio.getIdRubrica()==null) {
            throw new ReglaNegocioException("El id de la rubrica asociado al criterio NO puede ser nulo");
        }

        RubricaEntity objRubrica = this.rubricaRepository.findById(objCriterio.getIdRubrica()).orElse(null);

        if(objRubrica == null)
        {
            throw new EntidadNoExisteException("La rúbrica no existe");
        }

        objCriterioEntity.setRubrica(objRubrica);
        CriterioEntity objCriterioEntityGuardado = this.criterioRepository.save(objCriterioEntity);
        Criterio objCriterioGuardado = this.criterioMapper.map(objCriterioEntityGuardado, Criterio.class);
        objCriterioGuardado.setIdRubrica(objCriterioEntityGuardado.getRubrica().getIdRubrica());
        return objCriterioGuardado;
    }

    @Override
    public Criterio update(Long id, Criterio objCriterio) {
        CriterioEntity objCriterioActualizado;
        CriterioEntity objCriterioActual = this.criterioRepository.findById(id).orElse(null);
        if(objCriterioActual==null)
        {
            throw new EntidadNoExisteException("Criterio con el id {"+id+"} no existe");
        }
        objCriterioActual.setRubrica(rubricaRepository.findById(objCriterio.getIdRubrica()).orElse(null));
        objCriterioActual.setCrfDescripcion(objCriterio.getCrfDescripcion());
        objCriterioActual.setCrfPorcentaje(objCriterio.getCrfPorcentaje());
        objCriterioActual.setCrfNota(objCriterio.getCrfNota());
        objCriterioActual.setCrfComentario(objCriterio.getCrfComentario());
        objCriterioActualizado = this.criterioRepository.save(objCriterioActual);
        Criterio objCriterioRetorno = this.criterioMapper.map(objCriterioActualizado, Criterio.class);
        objCriterioRetorno.setIdRubrica(objCriterioRetorno.getRubrica().getIdRubrica());

        return objCriterioRetorno;
    }

    @Override
    public Criterio delete(Long idCriterio) {
        CriterioEntity criterioEliminar = this.criterioRepository.findById(idCriterio).orElse(null);
        if(criterioEliminar == null)
        {
            throw new EntidadNoExisteException("Criterio con el id {"+idCriterio+"} no existe");
        }
        Criterio criterioRetorno = this.criterioMapper.map(criterioEliminar, Criterio.class);
        this.criterioRepository.deleteById(idCriterio);
        criterioRetorno.setIdRubrica(criterioRetorno.getRubrica().getIdRubrica());
        return criterioRetorno;
    }

}
