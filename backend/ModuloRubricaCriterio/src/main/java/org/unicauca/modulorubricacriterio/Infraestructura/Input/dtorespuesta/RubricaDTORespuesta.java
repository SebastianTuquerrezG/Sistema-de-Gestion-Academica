package org.unicauca.modulorubricacriterio.Infraestructura.Input.dtorespuesta;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@AllArgsConstructor
public class RubricaDTORespuesta {
    private String idRubrica;
    private String nombreRubrica;
    private Long idMateria;
    private String nombreMateria;
    private int notaRubrica;
    private String objetivoEstudio;
    private List<CriterioDTORespuesta> criterios;
    private String estado;
    private Long raId;
}
