package org.unicauca.modulorubricacriterio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.unicauca.modulorubricacriterio.dto.CriterioDTO;
import org.unicauca.modulorubricacriterio.dto.RubricaDTO;
import org.unicauca.modulorubricacriterio.service.ICriterioService;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(value = "http://localhost:4200",
        methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class CriterioController {

    @Autowired
    private ICriterioService criterioService;

    @GetMapping("/criterios")
    public ResponseEntity<List<CriterioDTO>> getAllCriterios() {
        List<CriterioDTO>lista=criterioService.findAll();
        ResponseEntity<List<CriterioDTO>>response=new ResponseEntity<>(lista, HttpStatus.OK);
        return response;
    }

    @GetMapping("/criterios/{id}")
    public ResponseEntity<CriterioDTO> getById(@PathVariable("id") Long id) {
        CriterioDTO objCriterio = this.criterioService.findById(id);
        ResponseEntity<CriterioDTO>response=new ResponseEntity<>(objCriterio, HttpStatus.OK);
        return response;
    }

    @PostMapping("/criterios")
    public ResponseEntity<CriterioDTO>save(@RequestBody CriterioDTO criterioDTO) {
        CriterioDTO objCriterio = this.criterioService.save(criterioDTO);
        ResponseEntity<CriterioDTO>response=new ResponseEntity<>(objCriterio, HttpStatus.OK);
        return response;
    }


    @PutMapping("/criterios")
    public ResponseEntity<CriterioDTO>update(@RequestBody CriterioDTO criterioDTO,@RequestParam("id") Long id) {
        CriterioDTO objCriterio = this.criterioService.update(id,criterioDTO);
        System.out.println("id recibido"+id);
        ResponseEntity<CriterioDTO>response=new ResponseEntity<>(objCriterio, HttpStatus.OK);
        return response;
    }

    @DeleteMapping("/criterios")
    public ResponseEntity<CriterioDTO>delete(@RequestParam("id") Long id) {
        ResponseEntity<CriterioDTO>response=new ResponseEntity<>(null, HttpStatus.OK);
        if(criterioService.delete(id)) {
            response=new ResponseEntity<>(HttpStatus.OK);
        }
        return response;
    }



}
