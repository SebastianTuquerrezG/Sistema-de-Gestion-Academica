package org.unicauca.modulorubricacriterio.Infraestructura.Input.dtoPeticion;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;




@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CriterioDTOPeticion {
    private String crfDescripcion;
    private Float crfPorcentaje;
    private Float crfNota;
    private String crfComentario;
    private Long idRubrica;
}
