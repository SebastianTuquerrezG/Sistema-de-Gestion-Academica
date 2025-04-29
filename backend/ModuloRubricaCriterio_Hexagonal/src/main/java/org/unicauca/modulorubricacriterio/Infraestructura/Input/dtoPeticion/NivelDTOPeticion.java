package org.unicauca.modulorubricacriterio.Infraestructura.Input.dtoPeticion;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NivelDTOPeticion {
    private Long idCriterio;
    private String nivelDescripcion;
    private String rangoNota;
}
