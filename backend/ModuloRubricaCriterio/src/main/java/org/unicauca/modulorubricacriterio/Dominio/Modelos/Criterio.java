package org.unicauca.modulorubricacriterio.Dominio.Modelos;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Criterio {
    private Long idRubrica;
    private Long idCriterio;
    private Rubrica rubrica;
    private String crfDescripcion;
    private Integer crfPorcentaje;
    private Float crfNota;
    private String crfComentario;
    private List<Nivel> niveles;
}
