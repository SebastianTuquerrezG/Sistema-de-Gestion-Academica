package org.unicauca.modulorubricacriterio.Infraestructura.Input.dtoPeticion;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RubricaDTOPeticion {
    private Long idRubrica;
    private String nombreRubrica;
    private String materia;
    private Long idMateria;
    private int notaRubrica;
    private String objetivoEstudio;
    @NotNull(message = "{Rubricas.criteriosList}")
    @Size(min = 1, message = "{Rubricas.criteriosList.size}")
    @Valid 
    private List<@NotNull(message = "{Criterios.nivelesList.size}") CriterioDTOPeticion> criterios;
    private String estado;
    @NotNull(message = "{Rubrica.raId}")
    private Long raId;
}
