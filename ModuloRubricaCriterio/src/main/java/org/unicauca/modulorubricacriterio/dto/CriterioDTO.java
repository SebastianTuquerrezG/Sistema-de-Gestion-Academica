package org.unicauca.modulorubricacriterio.dto;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.unicauca.modulorubricacriterio.model.RubricaEntity;


import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CriterioDTO {

    private Long rubrica_id;
    private String crfDescripcion;
    private Float crfPorcentaje;
    private Float crfNota;
    private String crfComentario;


}
