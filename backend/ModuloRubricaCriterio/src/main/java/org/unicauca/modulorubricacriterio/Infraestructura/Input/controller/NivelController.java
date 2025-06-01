package org.unicauca.modulorubricacriterio.Infraestructura.Input.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.unicauca.modulorubricacriterio.Aplicacion.Input.IGestionNivelPort;
import org.unicauca.modulorubricacriterio.Dominio.Modelos.Nivel;
import org.unicauca.modulorubricacriterio.Infraestructura.Input.dtoPeticion.NivelDTOPeticion;
import org.unicauca.modulorubricacriterio.Infraestructura.Input.dtorespuesta.NivelDTORespuesta;
import org.unicauca.modulorubricacriterio.Infraestructura.Input.mappers.NivelMapperInfraDominio;

import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class NivelController {

    private final IGestionNivelPort nivelService;
    private final NivelMapperInfraDominio objNivelMapper;

    @GetMapping("/niveles")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN_ROLE', 'ROLE_TEACHER_ROLE', 'ROLE_COORDINATOR_ROLE', 'ROLE_STUDENT_ROLE')")
    public ResponseEntity<List<NivelDTORespuesta>> getAllNiveles() {
        List<Nivel>lista=nivelService.consultarNiveles();
        List<NivelDTORespuesta> listaDTO = objNivelMapper.mapNivelListToDtoList(lista);
        return new ResponseEntity<>(listaDTO, HttpStatus.OK);
    }

    @GetMapping("/niveles/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN_ROLE', 'ROLE_TEACHER_ROLE', 'ROLE_COORDINATOR_ROLE', 'ROLE_STUDENT_ROLE')")
    public ResponseEntity<NivelDTORespuesta> getById(@PathVariable("id") Long id) {
        Nivel objNivel = this.nivelService.consultarNivel(id);
        NivelDTORespuesta objNivelEncontrado = this.objNivelMapper.mapNivelToDto(objNivel);
        return new ResponseEntity<>(objNivelEncontrado, HttpStatus.OK);
    }

    @PostMapping("/niveles")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN_ROLE', 'ROLE_TEACHER_ROLE', 'ROLE_COORDINATOR_ROLE', 'ROLE_STUDENT_ROLE')")
    public ResponseEntity<NivelDTORespuesta>save(@Valid @RequestBody NivelDTOPeticion nivelDTO) {
        Nivel objNivelCrear = this.objNivelMapper.mapNivelDtoaNivel(nivelDTO);
        Nivel objNivelCreado = this.nivelService.crearNivel(objNivelCrear);
        NivelDTORespuesta objRespuesta = this.objNivelMapper.mapNivelToDto(objNivelCreado);
        return new ResponseEntity<>(objRespuesta, HttpStatus.CREATED);
    }


    @PutMapping("/niveles")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN_ROLE', 'ROLE_TEACHER_ROLE', 'ROLE_COORDINATOR_ROLE', 'ROLE_STUDENT_ROLE')")
    public ResponseEntity<NivelDTORespuesta>update(@Valid @RequestBody NivelDTOPeticion nivelDTO,@RequestParam("id") Long id) {
        Nivel objNivelActualizar = this.objNivelMapper.mapNivelDtoaNivel(nivelDTO);
        Nivel objNivelActualizado = this.nivelService.modificarNivel(id, objNivelActualizar);
        NivelDTORespuesta objRespuesta = this.objNivelMapper.mapNivelToDto(objNivelActualizado);
        return new ResponseEntity<>(objRespuesta, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/niveles")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN_ROLE', 'ROLE_TEACHER_ROLE', 'ROLE_COORDINATOR_ROLE', 'ROLE_STUDENT_ROLE')")
    public ResponseEntity<NivelDTORespuesta>delete(@RequestParam("id") Long id) {
        Nivel objNivelEliminar = this.nivelService.eliminarNivel(id);
        NivelDTORespuesta objNivelEliminado = this.objNivelMapper.mapNivelToDto(objNivelEliminar);
        return new ResponseEntity<>(objNivelEliminado, HttpStatus.OK);
    }
}