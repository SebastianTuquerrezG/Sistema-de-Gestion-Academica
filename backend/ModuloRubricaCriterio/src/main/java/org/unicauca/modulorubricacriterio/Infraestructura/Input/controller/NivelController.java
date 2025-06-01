package org.unicauca.modulorubricacriterio.Infraestructura.Input.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.unicauca.modulorubricacriterio.Aplicación.Input.IGestionNivelPort;
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
public class NivelController {

    private final IGestionNivelPort nivelService;
    private final NivelMapperInfraDominio objNivelMapper;

    NivelController(IGestionNivelPort nivelService, NivelMapperInfraDominio MapNivel) {
        this.nivelService = nivelService;
        this.objNivelMapper = MapNivel;
    }

    @GetMapping("/niveles")
    public ResponseEntity<List<NivelDTORespuesta>> getAllNiveles() {
        List<Nivel>lista=nivelService.consultarNiveles();
        List<NivelDTORespuesta> listaDTO = objNivelMapper.mapNivelListToDtoList(lista);
        ResponseEntity<List<NivelDTORespuesta>>response=new ResponseEntity<>(listaDTO, HttpStatus.OK);
        return response;
    }

    @GetMapping("/niveles/{id}")
    public ResponseEntity<NivelDTORespuesta> getById(@PathVariable("id") Long id) {
        Nivel objNivel = this.nivelService.consultarNivel(id);
        NivelDTORespuesta objNivelEncontrado = this.objNivelMapper.mapNivelToDto(objNivel);
        ResponseEntity<NivelDTORespuesta>response=new ResponseEntity<>(objNivelEncontrado, HttpStatus.OK);
        return response;
    }

    @PostMapping("/niveles")
    public ResponseEntity<NivelDTORespuesta>save(@Valid @RequestBody NivelDTOPeticion nivelDTO) {
        Nivel objNivelCrear = this.objNivelMapper.mapNivelDtoaNivel(nivelDTO);
        Nivel objNivelCreado = this.nivelService.crearNivel(objNivelCrear);
        NivelDTORespuesta objRespuesta = this.objNivelMapper.mapNivelToDto(objNivelCreado);
        ResponseEntity<NivelDTORespuesta> response = new ResponseEntity<NivelDTORespuesta>(objRespuesta, HttpStatus.CREATED);
        return response;
    }


    @PutMapping("/niveles")
    public ResponseEntity<NivelDTORespuesta>update(@Valid @RequestBody NivelDTOPeticion nivelDTO,@RequestParam("id") Long id) {
        Nivel objNivelActualizar = this.objNivelMapper.mapNivelDtoaNivel(nivelDTO);
        Nivel objNivelActualizado = this.nivelService.modificarNivel(id, objNivelActualizar);
        NivelDTORespuesta objRespuesta = this.objNivelMapper.mapNivelToDto(objNivelActualizado);
        ResponseEntity<NivelDTORespuesta> response = new ResponseEntity<NivelDTORespuesta>(objRespuesta, HttpStatus.ACCEPTED);
        return response;
    }

    @DeleteMapping("/niveles")
    public ResponseEntity<NivelDTORespuesta>delete(@RequestParam("id") Long id) {
        Nivel objNivelEliminar = this.nivelService.eliminarNivel(id);
        NivelDTORespuesta objNivelEliminado = this.objNivelMapper.mapNivelToDto(objNivelEliminar);
        ResponseEntity<NivelDTORespuesta>response=new ResponseEntity<>(objNivelEliminado, HttpStatus.OK);
        return response;
    }





}
