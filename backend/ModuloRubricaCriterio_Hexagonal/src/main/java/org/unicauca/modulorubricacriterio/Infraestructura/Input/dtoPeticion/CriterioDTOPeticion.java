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
public class CriterioDTOPeticion {
    private String crfDescripcion;
    private Float crfPorcentaje;
    private Float crfNota;
    private String crfComentario;
    @Size(min= 1, message = "{Criterios.nivelesList.size}")
    @NotNull(message = "{Criterios.nivelesList}")
    @Valid
    private List<NivelDTOPeticion> niveles;
    private Long idRubrica;
}
