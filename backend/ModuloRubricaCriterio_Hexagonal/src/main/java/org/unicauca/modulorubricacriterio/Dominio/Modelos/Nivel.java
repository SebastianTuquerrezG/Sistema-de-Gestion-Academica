package org.unicauca.modulorubricacriterio.Dominio.Modelos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Nivel {
    private Long idNivel;
    private Long idCriterio;
    private Criterio criterio;
    private String nivelDescripcion;
    private String rangoNota;

    /*Constructor no parametrizado
    usar para deserialización de datos*/
    public Nivel() { }
}
