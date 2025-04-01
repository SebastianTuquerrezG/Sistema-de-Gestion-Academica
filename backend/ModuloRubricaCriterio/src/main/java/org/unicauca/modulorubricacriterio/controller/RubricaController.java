package org.unicauca.modulorubricacriterio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.unicauca.modulorubricacriterio.Fachada.dto.RubricaDTO;
import org.unicauca.modulorubricacriterio.Fachada.service.IRubricaService;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(value = "*",
        methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class RubricaController {
    @Autowired
    private IRubricaService rubricaService;

    @GetMapping("/rubricas")
    public ResponseEntity<List<RubricaDTO>> getAllRubricas() {
        List<RubricaDTO>lista=rubricaService.findAll();
        ResponseEntity<List<RubricaDTO>>response=new ResponseEntity<>(lista, HttpStatus.OK);
        return response;
    }

    @PostMapping("/rubricas")
    public ResponseEntity<RubricaDTO>save(@RequestBody RubricaDTO rubricaDTO) {
        RubricaDTO objRubrica = this.rubricaService.save(rubricaDTO);
        ResponseEntity<RubricaDTO>response=new ResponseEntity<>(objRubrica, HttpStatus.CREATED);
        return response;
    }


    @PutMapping("/rubricas")
    public ResponseEntity<RubricaDTO>update(@RequestBody RubricaDTO rubricaDTO,@RequestParam("id") Long id) {
        RubricaDTO objRubrica = this.rubricaService.update(id,rubricaDTO);
        System.out.println("id recibido"+id);
        ResponseEntity<RubricaDTO>response=new ResponseEntity<>(objRubrica, HttpStatus.ACCEPTED);
        return response;
    }

    @PatchMapping("/rubricas")
    public ResponseEntity<RubricaDTO>delete(@RequestParam("id") Long id, @RequestBody RubricaDTO estado) {
        RubricaDTO objRubrica = this.rubricaService.delete(id,estado);
        ResponseEntity<RubricaDTO>response=new ResponseEntity<>(objRubrica, HttpStatus.OK);
        return response;
    }

    @GetMapping("/rubricas/{id}")
    public ResponseEntity<RubricaDTO>findById(@PathVariable("id") Long id) {
        RubricaDTO objRubrica = this.rubricaService.findById(id);
        ResponseEntity<RubricaDTO>response=new ResponseEntity<>(objRubrica, HttpStatus.OK);
        return response;
    }



}
