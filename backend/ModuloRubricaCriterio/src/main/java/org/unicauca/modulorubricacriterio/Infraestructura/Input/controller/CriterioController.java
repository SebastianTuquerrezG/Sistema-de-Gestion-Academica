package org.unicauca.modulorubricacriterio.Infraestructura.Input.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.unicauca.modulorubricacriterio.Aplicación.Input.IGestionCriterioPort;
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
@CrossOrigin(value = "*",
        methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.PATCH})
public class CriterioController {

    private final IGestionCriterioPort criterioService;
    private final CriterioMapperInfraDominio objCriterioMapper;

    CriterioController(IGestionCriterioPort criterioService, CriterioMapperInfraDominio MapCriterio) {
        this.criterioService = criterioService;
        this.objCriterioMapper = MapCriterio;
    }

    @GetMapping("/criterios")
    public ResponseEntity<List<CriterioDTORespuesta>> getAllCriterios() {
        List<Criterio> lista = criterioService.consultarCriterios();
        List<CriterioDTORespuesta>listaCriterioRespuesta=objCriterioMapper.mapCriterioListToDtoList(lista);
        ResponseEntity<List<CriterioDTORespuesta>>response=new ResponseEntity<>(listaCriterioRespuesta, HttpStatus.OK);
        return response;
    }

    @GetMapping("/criterios/{id}")
    public ResponseEntity<CriterioDTORespuesta> getById(@PathVariable("id") Long id) {
        Criterio objCriterio = this.criterioService.consultarCriterio(id);
        CriterioDTORespuesta objcriterioEncontrado = this.objCriterioMapper.mapCriterioToDto(objCriterio);
        ResponseEntity<CriterioDTORespuesta>response=new ResponseEntity<>(objcriterioEncontrado, HttpStatus.OK);
        return response;
    }

    @PostMapping("/criterios")
    public ResponseEntity<CriterioDTORespuesta>save(@Valid @RequestBody CriterioDTOPeticion criterioDTO) {
        Criterio objCriterioCrear = this.objCriterioMapper.mapDtoaCriterio(criterioDTO);
        Criterio objCriterioCreado = this.criterioService.crearCriterio(objCriterioCrear);
        CriterioDTORespuesta objCriterio = this.objCriterioMapper.mapCriterioToDto(objCriterioCreado);
        ResponseEntity<CriterioDTORespuesta>response=new ResponseEntity<>(objCriterio, HttpStatus.CREATED);
        return response;
    }


    @PutMapping("/criterios")
    public ResponseEntity<CriterioDTORespuesta>update(@Valid @RequestBody CriterioDTOPeticion criterioDTO,@RequestParam("id") Long id) {
        Criterio objCriterioActualizar = this.objCriterioMapper.mapDtoaCriterio(criterioDTO);
        Criterio objCriterioActualizado = this.criterioService.modificarCriterio(id, objCriterioActualizar);
        CriterioDTORespuesta objCriterio = this.objCriterioMapper.mapCriterioToDto(objCriterioActualizado);
        ResponseEntity<CriterioDTORespuesta>response=new ResponseEntity<>(objCriterio, HttpStatus.OK);
        return response;
    }

    @DeleteMapping("/criterios")
    public ResponseEntity<CriterioDTORespuesta>delete(@RequestParam("id") Long id) {
        Criterio objCriterioEliminar = this.criterioService.eliminarCriterio(id);
        CriterioDTORespuesta objCriterio = this.objCriterioMapper.mapCriterioToDto(objCriterioEliminar);
        ResponseEntity<CriterioDTORespuesta>response=new ResponseEntity<>(objCriterio, HttpStatus.OK);
        return response;
    }



}
