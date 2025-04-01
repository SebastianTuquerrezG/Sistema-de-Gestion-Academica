package org.unicauca.modulorubricacriterio.Fachada.dto;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.unicauca.modulorubricacriterio.AccesoADatos.model.RubricaEntity;



@Getter
@Setter
public class CriterioDTO {

    private Long rubrica_id;
    private String crfDescripcion;
    private Float crfPorcentaje;
    private Float crfNota;
    private String crfComentario;


}
