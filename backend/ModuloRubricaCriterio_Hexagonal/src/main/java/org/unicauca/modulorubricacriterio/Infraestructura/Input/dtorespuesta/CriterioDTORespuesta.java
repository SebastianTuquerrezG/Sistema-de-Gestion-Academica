package org.unicauca.modulorubricacriterio.Infraestructura.Input.dtorespuesta;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CriterioDTORespuesta {

    private Long idRubrica;
    private Long idCriterio;
    private String crfDescripcion;
    private Integer crfPorcentaje;
    private Float crfNota;
    private String crfComentario;
    private List<NivelDTORespuesta> niveles;


}
