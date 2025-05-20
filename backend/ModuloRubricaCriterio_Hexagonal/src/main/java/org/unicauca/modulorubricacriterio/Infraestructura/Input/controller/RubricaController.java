package org.unicauca.modulorubricacriterio.Infraestructura.Input.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.unicauca.modulorubricacriterio.Aplicacion.Input.IGestionRubricaPort;
import org.unicauca.modulorubricacriterio.Dominio.Modelos.Rubrica;
import org.unicauca.modulorubricacriterio.Infraestructura.Input.dtoPeticion.RubricaDTOPeticion;
import org.unicauca.modulorubricacriterio.Infraestructura.Input.dtorespuesta.RubricaDTORespuesta;
import org.unicauca.modulorubricacriterio.Infraestructura.Input.mappers.RubricaMapperInfraDominio;

import jakarta.validation.Valid;

import java.util.List;
/**
 * Controlador REST para gestionar las rúbricas.
 * Permite realizar operaciones CRUD sobre las rúbricas. Se conecta a la capa de aplicación
 * a través del puerto IGestionRubricaPort y utiliza un mapper para convertir entre entidades y DTOs.
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class RubricaController {
    
    private final IGestionRubricaPort rubricaService;
    private final RubricaMapperInfraDominio objRubricaMapper;

    @GetMapping("/rubricas")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN_ROLE', 'ROLE_TEACHER_ROLE', 'ROLE_COORDINATOR', 'ROLE_STUDENT_ROLE')")
    public ResponseEntity<List<RubricaDTORespuesta>> getAllRubricas() {
        List<Rubrica>lista=rubricaService.consultarRubricas();//Consultar la lista de rúbricas en el dominio
        List<RubricaDTORespuesta> listaDTO = objRubricaMapper.mapRubricaListToDtoList(lista);
        // for (RubricaDTORespuesta rubric : listaDTO) {
        //     rubric.setIdRubrica("IS10"+rubric.getIdRubrica());
        // }
        return new ResponseEntity<>(listaDTO, HttpStatus.OK);
    }

    @GetMapping("/rubricas/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN_ROLE', 'ROLE_TEACHER_ROLE', 'ROLE_COORDINATOR', 'ROLE_STUDENT_ROLE')")
    public ResponseEntity<RubricaDTORespuesta>findById(@PathVariable("id") Long id) {
        Rubrica objRubrica = this.rubricaService.consultarRubrica(id);//Consultar rúbrica en el dominio
        RubricaDTORespuesta objRubricaDTO = this.objRubricaMapper.mapRubricaToDto(objRubrica);
        // objRubricaDTO.setIdRubrica("IS10"+objRubrica.getIdRubrica());
        return new ResponseEntity<>(objRubricaDTO, HttpStatus.OK);
    }

    @PostMapping("/rubricas")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN_ROLE', 'ROLE_TEACHER_ROLE', 'ROLE_COORDINATOR', 'ROLE_STUDENT_ROLE')")
    public ResponseEntity<RubricaDTORespuesta>save(@Valid @RequestBody RubricaDTOPeticion rubricaDTO) {
        System.out.println(rubricaDTO);
        Rubrica objRubricaCrear = this.objRubricaMapper.mapDtoaRubrica(rubricaDTO);//MapearDto a rúbrica
        Rubrica objRubricaCreado = this.rubricaService.crearRubrica(objRubricaCrear);//Crear la rúbrica en el dominio
        RubricaDTORespuesta objRubrica = this.objRubricaMapper.mapRubricaToDto(objRubricaCreado);//Mapear la rúbrica creada a DTO
        // objRubrica.setIdRubrica("IS10"+objRubricaCreado.getIdRubrica());
        return new ResponseEntity<>(objRubrica, HttpStatus.CREATED);
    }


    @PutMapping("/rubricas")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN_ROLE', 'ROLE_TEACHER_ROLE', 'ROLE_COORDINATOR', 'ROLE_STUDENT_ROLE')")
    public ResponseEntity<RubricaDTORespuesta>update(@Valid @RequestBody RubricaDTOPeticion rubricaDTO,@RequestParam("id") Long id) {
        System.out.println("Id recibido"+id);
        Rubrica objRubricaActualizar = this.objRubricaMapper.mapDtoaRubrica(rubricaDTO);
        Rubrica objRubricaActualizado = this.rubricaService.modificarRubrica(id, objRubricaActualizar);
        RubricaDTORespuesta objRubrica = this.objRubricaMapper.mapRubricaToDto(objRubricaActualizado);
        // objRubrica.setIdRubrica("IS10"+objRubricaActualizado.getIdRubrica());
        return new ResponseEntity<>(objRubrica, HttpStatus.ACCEPTED);
    }

    @PatchMapping("/rubricas")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN_ROLE', 'ROLE_TEACHER_ROLE', 'ROLE_COORDINATOR', 'ROLE_STUDENT_ROLE')")
    public ResponseEntity<RubricaDTORespuesta>changeState(@RequestParam("id") Long id, @RequestBody RubricaDTOPeticion estado) {
        Rubrica objRubricaActualizado = this.rubricaService.editarEstadoRubrica(id, estado.getEstado());
        RubricaDTORespuesta objRubrica = this.objRubricaMapper.mapRubricaToDto(objRubricaActualizado);
        // objRubrica.setIdRubrica("IS10"+objRubricaActualizado.getIdRubrica());
        return new ResponseEntity<>(objRubrica, HttpStatus.OK);
    }
}