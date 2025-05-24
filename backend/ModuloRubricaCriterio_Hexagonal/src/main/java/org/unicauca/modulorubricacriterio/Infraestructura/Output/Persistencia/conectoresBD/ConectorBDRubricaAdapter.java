package org.unicauca.modulorubricacriterio.Infraestructura.Output.Persistencia.conectoresBD;

import java.util.List;
import java.util.ArrayList;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;
import org.unicauca.modulorubricacriterio.Aplicación.Output.IConectorBDRubricaPort;
import org.unicauca.modulorubricacriterio.Dominio.Modelos.Materia;
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
        MateriaEntity objMateriaEntity = this.materiaRepository.findById(objRubrica.getIdMateria()).orElseThrow(() -> new EntidadNoExisteException("Materia con el id {"+objRubrica.getIdMateria()+"} no existe"));
        RAEntity objRAEntity = this.raRepository.findById(objRubrica.getRaId()).orElseThrow(() -> new EntidadNoExisteException("RA con el id {"+objRubrica.getRaId()+"} no existe"));
        Integer numRubricasConNombre = this.existeRubricaConNombre(objRubrica.getNombreRubrica());
        //Para Guardar rúbricas duplicadas
        if(numRubricasConNombre >= 1) objRubricaEntity.setNombreRubrica(objRubrica.getNombreRubrica()+" ("+numRubricasConNombre+")");


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

        if (objRubricaEntity.getSubject() != null) {
            MateriaEntity objMateriaEntity = this.materiaRepository.findById(objRubrica.getIdMateria()).orElseThrow(() -> new EntidadNoExisteException("Materia con el id {"+objRubrica.getIdMateria()+"} no existe"));
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

        rubricaEncontrada.setCriterios(rubricaEncontrada.getCriterios() == null ? new ArrayList<>() : rubricaEncontrada.getCriterios());

        // Actualizar o agregar criterios
        for (int i = 0; i < objRubricaEntity.getCriterios().size(); i++) {
            var criterioNuevo = objRubricaEntity.getCriterios().get(i);
            
            if (i < rubricaEncontrada.getCriterios().size()) {
                var criterioExistente = rubricaEncontrada.getCriterios().get(i);
                criterioExistente.setCrfDescripcion(criterioNuevo.getCrfDescripcion());
                criterioExistente.setCrfNota(criterioNuevo.getCrfNota());
                criterioExistente.setCrfPorcentaje(criterioNuevo.getCrfPorcentaje());
                
                criterioExistente.setNiveles(
                    criterioExistente.getNiveles() == null ? new ArrayList<>() : criterioExistente.getNiveles()
                );

                // Actualizar o agregar niveles
                for (int j = 0; j < criterioNuevo.getNiveles().size(); j++) {
                    var nivelNuevo = criterioNuevo.getNiveles().get(j);
                    nivelNuevo.setCriterio(criterioExistente); // asociación

                    if (j < criterioExistente.getNiveles().size()) {
                        var nivelExistente = criterioExistente.getNiveles().get(j);
                        nivelExistente.setNivelDescripcion(nivelNuevo.getNivelDescripcion());
                        nivelExistente.setRangoNota(nivelNuevo.getRangoNota());
                    } else {
                        criterioExistente.getNiveles().add(nivelNuevo);
                    }
                }

                // Eliminar niveles sobrantes
                if (criterioExistente.getNiveles().size() > criterioNuevo.getNiveles().size()) {
                    criterioExistente.getNiveles().subList(criterioNuevo.getNiveles().size(),criterioExistente.getNiveles().size()).clear();
                }

            } else {
                // Agregar nuevo criterio
                criterioNuevo.setRubrica(rubricaEncontrada);

                // Asociar niveles con el nuevo criterio
                if (criterioNuevo.getNiveles() != null) {
                    for (var nivel : criterioNuevo.getNiveles()) {
                        nivel.setCriterio(criterioNuevo);
                    }
                }

                rubricaEncontrada.getCriterios().add(criterioNuevo);
            }
        }


    // Eliminar criterios sobrantes
    if (rubricaEncontrada.getCriterios().size() > objRubricaEntity.getCriterios().size()) {
        rubricaEncontrada.getCriterios().subList(objRubricaEntity.getCriterios().size(),rubricaEncontrada.getCriterios().size()).clear();
    }

        rubricaEncontrada.setSubject(objRubricaEntity.getSubject());
        rubricaEncontrada.setNombreRubrica(objRubrica.getNombreRubrica());
        rubricaEncontrada.setNotaRubrica(objRubrica.getNotaRubrica());
        rubricaEncontrada.setObjetivoEstudio(objRubrica.getObjetivoEstudio());
        rubricaEncontrada.setRa(objRubricaEntity.getRa());


        objRubricaActualizada = this.rubricaRepository.save(rubricaEncontrada);
        Rubrica rubricaActualizadaReturn = this.rubricaMapper.map(objRubricaActualizada, Rubrica.class);

        rubricaActualizadaReturn.setNombreMateria(this.rubricaRepository.findSubjectNameByRubricaId(id));
        
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

    /**
     * @brief método que verifica que existe una rúbrica con el nombre dado
     * @param nombreRubrica nombre de la rúbrica a verificar
     * @return número de coincidencias de rúbricas con el nombre dado
     */
    private Integer existeRubricaConNombre(String nombreRubrica) {
        Integer coincidencias = this.rubricaRepository.existeRubricaConNombre(nombreRubrica);
        return coincidencias;
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

    @Override
    public List<Materia> findAllMaterias() {
        List<MateriaEntity> listMateriaEntity = this.materiaRepository.findAll();
        List<Materia> listMateria = this.rubricaMapper.map(listMateriaEntity, new TypeToken<List<Materia>>() {
        }.getType());
        return listMateria;
    }
    

}
