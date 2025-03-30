package org.unicauca.modulorubricacriterio.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.unicauca.modulorubricacriterio.model.CriterioEntity;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RubricaDTO {
    private String nombreRubrica;
    private int notaRubrica;
    private String objetivoEstudio;
    private List<CriterioEntity> criterios;
}
