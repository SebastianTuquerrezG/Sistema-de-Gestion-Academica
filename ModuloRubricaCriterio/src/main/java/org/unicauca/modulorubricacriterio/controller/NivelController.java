package org.unicauca.modulorubricacriterio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.unicauca.modulorubricacriterio.dto.CriterioDTO;
import org.unicauca.modulorubricacriterio.dto.NivelDTO;
import org.unicauca.modulorubricacriterio.service.INivelService;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(value = "http://localhost:4200",
        methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class NivelController {

    @Autowired
    private INivelService nivelService;

    @GetMapping("/niveles")
    public ResponseEntity<List<NivelDTO>> getAllNiveles() {
        List<NivelDTO>lista=nivelService.findAll();
        ResponseEntity<List<NivelDTO>>response=new ResponseEntity<>(lista, HttpStatus.OK);
        return response;
    }

    @PostMapping("/niveles")
    public ResponseEntity<NivelDTO>save(@RequestBody NivelDTO nivelDTO) {
        NivelDTO objNivel = this.nivelService.save(nivelDTO);
        ResponseEntity<NivelDTO>response=new ResponseEntity<>(objNivel, HttpStatus.OK);
        return response;
    }


    @PutMapping("/niveles")
    public ResponseEntity<NivelDTO>update(@RequestBody NivelDTO nivelDTO,@RequestParam("id") Long id) {
        NivelDTO objNivel = this.nivelService.update(id,nivelDTO);
        System.out.println("id recibido"+id);
        ResponseEntity<NivelDTO>response=new ResponseEntity<>(objNivel, HttpStatus.OK);
        return response;
    }

    @DeleteMapping("/niveles")
    public ResponseEntity<NivelDTO>delete(@RequestParam("id") Long id) {
        ResponseEntity<NivelDTO>response=new ResponseEntity<>(null, HttpStatus.OK);
        if(nivelService.delete(id)) {
            response=new ResponseEntity<>(HttpStatus.OK);
        }
        return response;
    }

    @GetMapping("/niveles/{id}")
    public ResponseEntity<NivelDTO> getById(@PathVariable("id") Long id) {
        NivelDTO objNivel = this.nivelService.findById(id);
        ResponseEntity<NivelDTO>response=new ResponseEntity<>(objNivel, HttpStatus.OK);
        return response;
    }



}
