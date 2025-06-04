package org.unicauca.modulorubricacriterio.Infraestructura.Input.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.unicauca.modulorubricacriterio.Aplicacion.Input.IGestionCriterioPort;
import org.unicauca.modulorubricacriterio.Dominio.Modelos.Criterio;
import org.unicauca.modulorubricacriterio.Infraestructura.Input.dtoPeticion.CriterioDTOPeticion;
import org.unicauca.modulorubricacriterio.Infraestructura.Input.dtorespuesta.CriterioDTORespuesta;
import org.unicauca.modulorubricacriterio.Infraestructura.Input.mappers.CriterioMapperInfraDominio;

import jakarta.validation.Valid;

import java.util.List;


/**
 * Controlador REST para gestionar los criterios.
 * Permite realizar operaciones CRUD sobre los criterios. Se conecta a la capa de aplicación
 * a través del puerto IGestionCriterioPort y utiliza un mapper para convertir entre entidades y DTOs.
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CriterioController {

    private final IGestionCriterioPort criterioService;
    private final CriterioMapperInfraDominio objCriterioMapper;

    @GetMapping("/criterios")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN_ROLE', 'ROLE_COORDINATOR_ROLE', 'ROLE_TEACHER_ROLE_ROLE', 'ROLE_STUDENT_ROLE')")
    public ResponseEntity<List<CriterioDTORespuesta>> getAllCriterios() {
        List<Criterio> lista = criterioService.consultarCriterios();
        List<CriterioDTORespuesta>listaCriterioRespuesta=objCriterioMapper.mapCriterioListToDtoList(lista);
        return new ResponseEntity<>(listaCriterioRespuesta, HttpStatus.OK);
    }

    @GetMapping("/criterios/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN_ROLE', 'ROLE_TEACHER_ROLE', 'ROLE_COORDINATOR_ROLE', 'ROLE_STUDENT_ROLE')")
    public ResponseEntity<CriterioDTORespuesta> getById(@PathVariable("id") Long id) {
        Criterio objCriterio = this.criterioService.consultarCriterio(id);
        CriterioDTORespuesta objcriterioEncontrado = this.objCriterioMapper.mapCriterioToDto(objCriterio);
        return new ResponseEntity<>(objcriterioEncontrado, HttpStatus.OK);
    }

    @PostMapping("/criterios")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN_ROLE', 'ROLE_TEACHER_ROLE', 'ROLE_COORDINATOR_ROLE', 'ROLE_STUDENT_ROLE')")
    public ResponseEntity<CriterioDTORespuesta>save(@Valid @RequestBody CriterioDTOPeticion criterioDTO) {
        Criterio objCriterioCrear = this.objCriterioMapper.mapDtoaCriterio(criterioDTO);
        Criterio objCriterioCreado = this.criterioService.crearCriterio(objCriterioCrear);
        CriterioDTORespuesta objCriterio = this.objCriterioMapper.mapCriterioToDto(objCriterioCreado);
        return new ResponseEntity<>(objCriterio, HttpStatus.CREATED);
    }


    @PutMapping("/criterios")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN_ROLE', 'ROLE_TEACHER_ROLE', 'ROLE_COORDINATOR_ROLE', 'ROLE_STUDENT_ROLE')")
    public ResponseEntity<CriterioDTORespuesta>update(@Valid @RequestBody CriterioDTOPeticion criterioDTO,@RequestParam("id") Long id) {
        Criterio objCriterioActualizar = this.objCriterioMapper.mapDtoaCriterio(criterioDTO);
        Criterio objCriterioActualizado = this.criterioService.modificarCriterio(id, objCriterioActualizar);
        CriterioDTORespuesta objCriterio = this.objCriterioMapper.mapCriterioToDto(objCriterioActualizado);
        return new ResponseEntity<>(objCriterio, HttpStatus.OK);
    }

    @DeleteMapping("/criterios")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN_ROLE', 'ROLE_TEACHER_ROLE', 'ROLE_COORDINATOR_ROLE', 'ROLE_STUDENT_ROLE')")
    public ResponseEntity<CriterioDTORespuesta>delete(@RequestParam("id") Long id) {
        Criterio objCriterioEliminar = this.criterioService.eliminarCriterio(id);
        CriterioDTORespuesta objCriterio = this.objCriterioMapper.mapCriterioToDto(objCriterioEliminar);
        return new ResponseEntity<>(objCriterio, HttpStatus.OK);
    }
}