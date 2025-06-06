package org.unicauca.modulorubricacriterio.Infraestructura.Input.dtorespuesta;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class NivelDTORespuesta {
    private Long idNivel;
    private Long idCriterio;
    private String nivelDescripcion;
    private String rangoNota;
}
