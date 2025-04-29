package org.unicauca.modulorubricacriterio.Infraestructura.Output.Persistencia.conectoresBD;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;
import org.unicauca.modulorubricacriterio.Aplicación.Output.IConectorBDRubricaPort;

import org.unicauca.modulorubricacriterio.Dominio.Modelos.Rubrica;
import org.unicauca.modulorubricacriterio.Infraestructura.Fachada.exception.exceptionOwn.CambioInvalidoException;
import org.unicauca.modulorubricacriterio.Infraestructura.Fachada.exception.exceptionOwn.EntidadNoExisteException;
import org.unicauca.modulorubricacriterio.Infraestructura.Input.validacionEstados.EstadosController;
import org.unicauca.modulorubricacriterio.Infraestructura.Input.validacionEstados.EstadosEnum;
import org.unicauca.modulorubricacriterio.Infraestructura.Output.Persistencia.entity.MateriaEntity;
import org.unicauca.modulorubricacriterio.Infraestructura.Output.Persistencia.entity.RAEntity;
import org.unicauca.modulorubricacriterio.Infraestructura.Output.Persistencia.entity.RubricaEntity;
import org.unicauca.modulorubricacriterio.Infraestructura.Output.Persistencia.repository.MateriaRepository;
import org.unicauca.modulorubricacriterio.Infraestructura.Output.Persistencia.repository.RARepository;
import org.unicauca.modulorubricacriterio.Infraestructura.Output.Persistencia.repository.RubricaRepository;

@Service
public class ConectorBDRubricaAdapter implements IConectorBDRubricaPort {

    private final RubricaRepository rubricaRepository;
    private final ModelMapper rubricaMapper;
    private final MateriaRepository materiaRepository;
    private final RARepository raRepository;

    public ConectorBDRubricaAdapter(RubricaRepository rubricaRepository, ModelMapper rubricaMapper, MateriaRepository materiaRepository, RARepository raRepository) {
        this.rubricaRepository = rubricaRepository;
        this.rubricaMapper = rubricaMapper;
        this.materiaRepository = materiaRepository;
        this.raRepository = raRepository;
    }

    @Override
    public List<Rubrica> findAll() {
        List<RubricaEntity> listRubricaEntity = this.rubricaRepository.findAll();
        List<Rubrica> listRubrica = this.rubricaMapper.map(listRubricaEntity, new TypeToken<List<Rubrica>>() {
        }.getType());
        for (Rubrica rubric : listRubrica) {
            if(rubric.getCriterios() != null)
            {
                rubric.getCriterios().forEach(criterio -> {
                    criterio.setIdRubrica(rubric.getIdRubrica());
                    if(criterio.getNiveles() != null) {
                        criterio.getNiveles().forEach(nivel -> nivel.setIdCriterio(criterio.getIdCriterio()));
                    }
                });
            }
        }
        return listRubrica;
    }

    @Override
    public Rubrica findById(Long idRubrica) {
        RubricaEntity objRubricaEntity = this.rubricaRepository.findById(idRubrica).orElse(null);

        if (objRubricaEntity == null) {
            throw new EntidadNoExisteException("Rúbrica con el id {"+idRubrica+"} no existe");
        }

        Rubrica objRubrica = this.rubricaMapper.map(objRubricaEntity, Rubrica.class);
        if(objRubrica.getCriterios()!= null)
        {
            objRubrica.getCriterios().forEach(criterio -> {
                criterio.setIdRubrica(objRubrica.getIdRubrica());
                if(criterio.getNiveles() != null) {
                    criterio.getNiveles().forEach(nivel -> nivel.setIdCriterio(criterio.getIdCriterio()));
                }
            });

        }
        
        return objRubrica;
    }

    @Override
    public Rubrica saveRubric(Rubrica objRubrica) {
        RubricaEntity objRubricaEntity = this.rubricaMapper.map(objRubrica, RubricaEntity.class);
        MateriaEntity objMateriaEntity = this.materiaRepository.findById(objRubrica.getMateria()).orElseThrow(() -> new EntidadNoExisteException("Materia con el id {"+objRubrica.getMateria()+"} no existe"));
        RAEntity objRAEntity = this.raRepository.findById(objRubrica.getRaId()).orElseThrow(() -> new EntidadNoExisteException("RA con el id {"+objRubrica.getRaId()+"} no existe"));

        objRubricaEntity.setSubject(objMateriaEntity);
        objRubricaEntity.setRa(objRAEntity);

        if(objRubricaEntity.getCriterios() != null)
        {
            objRubricaEntity.getCriterios().forEach(criterio -> {
                criterio.setRubrica(objRubricaEntity);
                if(criterio.getNiveles() != null) {
                    criterio.getNiveles().forEach(nivel -> nivel.setCriterio(criterio));
                }
            });
        }
        RubricaEntity objRubricaEntityGuardada = this.rubricaRepository.save(objRubricaEntity);
        Rubrica objRubricaGuardada = this.rubricaMapper.map(objRubricaEntityGuardada, Rubrica.class);

        if(objRubricaGuardada.getCriterios() != null)
        {
            objRubricaGuardada.getCriterios().forEach(criterio -> {
                criterio.setIdRubrica(objRubricaGuardada.getIdRubrica());
                if(criterio.getNiveles() != null) {
                    criterio.getNiveles().forEach(nivel -> nivel.setIdCriterio(criterio.getIdCriterio()));
                }
            });
        }
        return objRubricaGuardada;
    }

    @Override
    public Rubrica updateRubric(Long id, Rubrica objRubrica) {
        RubricaEntity objRubricaActualizada = null;
        RubricaEntity objRubricaEntity = this.rubricaMapper.map(objRubrica, RubricaEntity.class);
        RubricaEntity rubricaEncontrada = this.rubricaRepository.findById(id).orElse(null);

        if (objRubricaEntity.getSubject() != null) {
            MateriaEntity objMateriaEntity = this.materiaRepository.findById(objRubrica.getMateria()).orElseThrow(() -> new EntidadNoExisteException("Materia con el id {"+objRubrica.getMateria()+"} no existe"));
            objRubricaEntity.setSubject(objMateriaEntity);
        } else {
            assert rubricaEncontrada != null;
            objRubricaEntity.setSubject(rubricaEncontrada.getSubject());
        }
        if (objRubricaEntity.getRa() != null) {
            RAEntity objRAEntity = this.raRepository.findById(objRubrica.getRaId()).orElseThrow(() -> new EntidadNoExisteException("RA con el id {"+objRubrica.getRaId()+"} no existe"));
            objRubricaEntity.setRa(objRAEntity);
        } else {
            assert rubricaEncontrada != null;
            objRubricaEntity.setRa(rubricaEncontrada.getRa());
        }
        if(rubricaEncontrada==null)
        {
            throw new EntidadNoExisteException("Rúbrica con el id {"+id+"} no existe");
        }

        objRubricaEntity.getCriterios().forEach(criterio -> {
            criterio.setRubrica(rubricaEncontrada);
            if(criterio.getNiveles() != null) {
                criterio.getNiveles().forEach(nivel -> nivel.setCriterio(criterio));
            }
        });

        for(int i=0; i < objRubricaEntity.getCriterios().size(); i++)
        {
            objRubricaEntity.getCriterios().get(i).setIdCriterio(rubricaEncontrada.getCriterios().get(i).getIdCriterio());
            for(int j=0; j < objRubricaEntity.getCriterios().get(i).getNiveles().size(); j++)
            {
                objRubricaEntity.getCriterios().get(i).getNiveles().get(j).setIdNivel(rubricaEncontrada.getCriterios().get(i).getNiveles().get(j).getIdNivel());
            }
        }



        rubricaEncontrada.setSubject(objRubricaEntity.getSubject());
        rubricaEncontrada.setNombreRubrica(objRubrica.getNombreRubrica());
        rubricaEncontrada.setNotaRubrica(objRubrica.getNotaRubrica());
        rubricaEncontrada.setObjetivoEstudio(objRubrica.getObjetivoEstudio());
        rubricaEncontrada.setCriterios(objRubricaEntity.getCriterios());
        rubricaEncontrada.setRa(objRubricaEntity.getRa());


        objRubricaActualizada = this.rubricaRepository.save(rubricaEncontrada);
        Rubrica rubricaActualizadaReturn = this.rubricaMapper.map(objRubricaActualizada, Rubrica.class);
        
        if(rubricaActualizadaReturn.getCriterios() != null)
        {
            rubricaActualizadaReturn.getCriterios().forEach(criterio -> {
                criterio.setIdRubrica(rubricaActualizadaReturn.getIdRubrica());
                if(criterio.getNiveles() != null) {
                    criterio.getNiveles().forEach(nivel -> nivel.setIdCriterio(criterio.getIdCriterio()));
                }
            });
        }

        return rubricaActualizadaReturn;   
    }

    @Override
    public Rubrica deleteRubric(Long IdRubrica) {
        RubricaEntity objRubricaAEliminar = this.rubricaRepository.findById(IdRubrica).orElse(null);
        if(objRubricaAEliminar == null)
        {
            throw new EntidadNoExisteException("Rúbrica con el id {"+IdRubrica+"} no existe");
        }
        Rubrica objRubrica = this.rubricaMapper.map(objRubricaAEliminar, Rubrica.class);
        this.rubricaRepository.deleteById(IdRubrica);
        return objRubrica;
    }

    @Override
    public Rubrica changeEstate(Long idRubrica, String estadoRubrica) {
        RubricaEntity objRubricaActualizada = null;
        EstadosEnum estadoSiguiente = validarEstado(estadoRubrica);
        RubricaEntity rubricaEncontrada = this.rubricaRepository.findById(idRubrica).orElse(null);
        if(rubricaEncontrada==null)
        {
            throw new EntidadNoExisteException("Rúbrica con el id {"+idRubrica+"} no existe");
        }

        EstadosController objEstadoActual  = new EstadosController(rubricaEncontrada.getEstadoRubrica().ordinal());

        if(objEstadoActual.cambioEstado(estadoRubrica).cambioPermitido())
        {
            rubricaEncontrada.setEstadoRubrica(estadoSiguiente);
        }else
        {
            throw new CambioInvalidoException(objEstadoActual.cambioEstado(estadoRubrica).mensaje());
        }

        objRubricaActualizada = this.rubricaRepository.save(rubricaEncontrada);

        Rubrica rubricaRetornar = this.rubricaMapper.map(objRubricaActualizada, Rubrica.class);
        
        if(rubricaRetornar.getCriterios() != null)
        {
            rubricaRetornar.getCriterios().forEach(criterio -> {
                criterio.setIdRubrica(rubricaRetornar.getIdRubrica());
                if(criterio.getNiveles() != null) {
                    criterio.getNiveles().forEach(nivel -> nivel.setIdCriterio(criterio.getIdCriterio()));
                }
            });
        }
        

        return rubricaRetornar;
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
