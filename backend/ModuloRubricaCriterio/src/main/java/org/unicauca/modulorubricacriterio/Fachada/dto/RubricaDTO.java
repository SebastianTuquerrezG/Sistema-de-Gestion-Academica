package org.unicauca.modulorubricacriterio.Fachada.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.unicauca.modulorubricacriterio.AccesoADatos.model.CriterioEntity;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RubricaDTO {
    private String rubricaId;
    private String nombreRubrica;
    private String materia;
    private int notaRubrica;
    private String objetivoEstudio;
    private List<CriterioEntity> criterios;
    private String estado;
}
