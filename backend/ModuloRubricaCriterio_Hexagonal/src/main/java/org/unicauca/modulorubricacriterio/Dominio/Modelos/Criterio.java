package org.unicauca.modulorubricacriterio.Dominio.Modelos;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Criterio {
    private Long idRubrica;
    private Long idCriterio;
    private Rubrica rubrica;
    private String crfDescripcion;
    private Float crfPorcentaje;
    private Float crfNota;
    private String crfComentario;
    private List<Nivel> niveles;

    /*Constructor no parametrizado
    usar para deserialización de datos*/
    public Criterio() { }

}
