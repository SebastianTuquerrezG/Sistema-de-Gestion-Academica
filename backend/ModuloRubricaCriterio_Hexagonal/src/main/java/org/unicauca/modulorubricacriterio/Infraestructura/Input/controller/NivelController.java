package org.unicauca.modulorubricacriterio.Infraestructura.Input.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
@CrossOrigin(value = "*",
        methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.PATCH})
@RequiredArgsConstructor
public class NivelController {

    private final IGestionNivelPort nivelService;
    private final NivelMapperInfraDominio objNivelMapper;

    @GetMapping("/niveles")
    public ResponseEntity<List<NivelDTORespuesta>> getAllNiveles() {
        List<Nivel>lista=nivelService.consultarNiveles();
        List<NivelDTORespuesta> listaDTO = objNivelMapper.mapNivelListToDtoList(lista);
        return new ResponseEntity<>(listaDTO, HttpStatus.OK);
    }

    @GetMapping("/niveles/{id}")
    public ResponseEntity<NivelDTORespuesta> getById(@PathVariable("id") Long id) {
        Nivel objNivel = this.nivelService.consultarNivel(id);
        NivelDTORespuesta objNivelEncontrado = this.objNivelMapper.mapNivelToDto(objNivel);
        return new ResponseEntity<>(objNivelEncontrado, HttpStatus.OK);
    }

    @PostMapping("/niveles")
    public ResponseEntity<NivelDTORespuesta>save(@Valid @RequestBody NivelDTOPeticion nivelDTO) {
        Nivel objNivelCrear = this.objNivelMapper.mapNivelDtoaNivel(nivelDTO);
        Nivel objNivelCreado = this.nivelService.crearNivel(objNivelCrear);
        NivelDTORespuesta objRespuesta = this.objNivelMapper.mapNivelToDto(objNivelCreado);
        return new ResponseEntity<NivelDTORespuesta>(objRespuesta, HttpStatus.CREATED);
    }


    @PutMapping("/niveles")
    public ResponseEntity<NivelDTORespuesta>update(@Valid @RequestBody NivelDTOPeticion nivelDTO,@RequestParam("id") Long id) {
        Nivel objNivelActualizar = this.objNivelMapper.mapNivelDtoaNivel(nivelDTO);
        Nivel objNivelActualizado = this.nivelService.modificarNivel(id, objNivelActualizar);
        NivelDTORespuesta objRespuesta = this.objNivelMapper.mapNivelToDto(objNivelActualizado);
        return new ResponseEntity<NivelDTORespuesta>(objRespuesta, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/niveles")
    public ResponseEntity<NivelDTORespuesta>delete(@RequestParam("id") Long id) {
        Nivel objNivelEliminar = this.nivelService.eliminarNivel(id);
        NivelDTORespuesta objNivelEliminado = this.objNivelMapper.mapNivelToDto(objNivelEliminar);
        return new ResponseEntity<>(objNivelEliminado, HttpStatus.OK);
    }
}