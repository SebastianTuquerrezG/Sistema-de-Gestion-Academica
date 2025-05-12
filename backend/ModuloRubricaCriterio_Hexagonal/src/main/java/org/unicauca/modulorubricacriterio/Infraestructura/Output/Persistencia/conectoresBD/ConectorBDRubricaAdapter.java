package org.unicauca.modulorubricacriterio.Infraestructura.Output.Persistencia.conectoresBD;

import java.util.ArrayList;
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
<<<<<<< Updated upstream
=======
import org.unicauca.modulorubricacriterio.Infraestructura.Output.Persistencia.entity.CriterioEntity;
import org.unicauca.modulorubricacriterio.Infraestructura.Output.Persistencia.entity.MateriaEntity;
import org.unicauca.modulorubricacriterio.Infraestructura.Output.Persistencia.entity.RAEntity;
>>>>>>> Stashed changes
import org.unicauca.modulorubricacriterio.Infraestructura.Output.Persistencia.entity.RubricaEntity;
import org.unicauca.modulorubricacriterio.Infraestructura.Output.Persistencia.repository.RubricaRepository;

@Service
public class ConectorBDRubricaAdapter implements IConectorBDRubricaPort {

    private final RubricaRepository rubricaRepository;
    private final ModelMapper rubricaMapper;

    public ConectorBDRubricaAdapter(RubricaRepository rubricaRepository, ModelMapper rubricaMapper) {
        this.rubricaRepository = rubricaRepository;
        this.rubricaMapper = rubricaMapper;
    }

    @Override
    public List<Rubrica> findAll() {
        List<RubricaEntity> listRubricaEntity = this.rubricaRepository.findAll();
        List<Rubrica> listRubrica = this.rubricaMapper.map(listRubricaEntity, new TypeToken<List<Rubrica>>() {
        }.getType());
        for (Rubrica rubric : listRubrica) {
            rubric.setNombreMateria(this.rubricaRepository.findSubjectNameByRubricaId(rubric.getIdRubrica()));
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
        objRubrica.setNombreMateria(this.rubricaRepository.findSubjectNameByRubricaId(objRubrica.getIdRubrica()));
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
<<<<<<< Updated upstream
=======
        MateriaEntity objMateriaEntity = this.materiaRepository.findById(objRubrica.getIdMateria()).orElseThrow(() -> new EntidadNoExisteException("Materia con el id {"+objRubrica.getIdMateria()+"} no existe"));
        RAEntity objRAEntity = this.raRepository.findById(objRubrica.getRaId()).orElseThrow(() -> new EntidadNoExisteException("RA con el id {"+objRubrica.getRaId()+"} no existe"));

        objRubricaEntity.setSubject(objMateriaEntity);
        objRubricaEntity.setRa(objRAEntity);

>>>>>>> Stashed changes
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
        objRubricaGuardada.setNombreMateria(this.rubricaRepository.findSubjectNameByRubricaId(objRubricaGuardada.getIdRubrica()));

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
<<<<<<< Updated upstream
        if(rubricaEncontrada==null)
        {
            throw new EntidadNoExisteException("Rúbrica con el id {"+id+"} no existe");
=======

        if (objRubricaEntity.getSubject() != null) {
            MateriaEntity objMateriaEntity = this.materiaRepository.findById(objRubrica.getIdMateria())
                    .orElseThrow(() -> new EntidadNoExisteException("Materia con el id {" + objRubrica.getIdMateria() + "} no existe"));
            objRubricaEntity.setSubject(objMateriaEntity);
        } else {
            assert rubricaEncontrada != null;
            objRubricaEntity.setSubject(rubricaEncontrada.getSubject());
        }

        if (objRubricaEntity.getRa() != null) {
            RAEntity objRAEntity = this.raRepository.findById(objRubrica.getRaId())
                    .orElseThrow(() -> new EntidadNoExisteException("RA con el id {" + objRubrica.getRaId() + "} no existe"));
            objRubricaEntity.setRa(objRAEntity);
        } else {
            assert rubricaEncontrada != null;
            objRubricaEntity.setRa(rubricaEncontrada.getRa());
        }

        if (rubricaEncontrada == null) {
            throw new EntidadNoExisteException("Rúbrica con el id {" + id + "} no existe");
>>>>>>> Stashed changes
        }

        rubricaEncontrada.setCriterios(rubricaEncontrada.getCriterios() == null ? new ArrayList<>() : rubricaEncontrada.getCriterios());

        // Actualizar o agregar criterios
        for (int i = 0; i < objRubricaEntity.getCriterios().size(); i++) {
            if (i < rubricaEncontrada.getCriterios().size()) {
                var criterioExistente = rubricaEncontrada.getCriterios().get(i);
                var criterioNuevo = objRubricaEntity.getCriterios().get(i);
                criterioExistente.setCrfDescripcion(criterioNuevo.getCrfDescripcion());
                criterioExistente.setCrfNota(criterioNuevo.getCrfNota());
                criterioExistente.setCrfPorcentaje(criterioNuevo.getCrfPorcentaje());
                criterioExistente.setNiveles(criterioExistente.getNiveles() == null ? new ArrayList<>() : criterioExistente.getNiveles());

                // Actualizar niveles y asociarlos correctamente con el criterio
                for (int j = 0; j < criterioNuevo.getNiveles().size(); j++) {
                    var nivelExistente = j < criterioExistente.getNiveles().size() ? criterioExistente.getNiveles().get(j) : null;
                    var nivelNuevo = criterioNuevo.getNiveles().get(j);

                    if (nivelExistente != null) {
                        // Actualizar nivel existente
                        nivelExistente.setNivelDescripcion(nivelNuevo.getNivelDescripcion());
                        nivelExistente.setRangoNota(nivelNuevo.getRangoNota());
                    } else {
                        // Agregar nuevo nivel
                        criterioExistente.getNiveles().add(nivelNuevo);
                    }
                    // Asegurarse de que cada nivel tenga el Criterio correspondiente
                    nivelNuevo.setCriterio(criterioExistente);
                }

                // Eliminar niveles sobrantes
                criterioExistente.getNiveles().subList(criterioNuevo.getNiveles().size(), criterioExistente.getNiveles().size()).clear();
            } else {
                // Agregar nuevo criterio
                rubricaEncontrada.getCriterios().add(objRubricaEntity.getCriterios().get(i));
            }
        }

<<<<<<< Updated upstream
        rubricaEncontrada.setMateria(objRubrica.getMateria());
        rubricaEncontrada.setNombreRubrica(objRubrica.getNombreRubrica());
        rubricaEncontrada.setNotaRubrica(objRubrica.getNotaRubrica());
        rubricaEncontrada.setObjetivoEstudio(objRubrica.getObjetivoEstudio());
        rubricaEncontrada.setCriterios(objRubricaEntity.getCriterios());
=======
        // Eliminar criterios sobrantes
        rubricaEncontrada.getCriterios().subList(objRubricaEntity.getCriterios().size(), rubricaEncontrada.getCriterios().size()).clear();

        // Actualizar campos principales de la rubrica
        rubricaEncontrada.setSubject(objRubricaEntity.getSubject());
        rubricaEncontrada.setNombreRubrica(objRubrica.getNombreRubrica());
        rubricaEncontrada.setNotaRubrica(objRubrica.getNotaRubrica());
        rubricaEncontrada.setObjetivoEstudio(objRubrica.getObjetivoEstudio());
        rubricaEncontrada.setRa(objRubricaEntity.getRa());
>>>>>>> Stashed changes

        objRubricaActualizada = this.rubricaRepository.save(rubricaEncontrada);

        Rubrica rubricaActualizadaReturn = this.rubricaMapper.map(objRubricaActualizada, Rubrica.class);
        rubricaActualizadaReturn.setNombreMateria(this.rubricaRepository.findSubjectNameByRubricaId(id));

        if (rubricaActualizadaReturn.getCriterios() != null) {
            rubricaActualizadaReturn.getCriterios().forEach(criterio -> {
                criterio.setIdRubrica(rubricaActualizadaReturn.getIdRubrica());
                if (criterio.getNiveles() != null) {
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
