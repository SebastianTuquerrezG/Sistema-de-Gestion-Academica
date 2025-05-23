package org.unicauca.modulorubricacriterio.Infraestructura.Input.dtoPeticion;

import jakarta.validation.constraints.NotBlank;
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
    @NotBlank(message = "{Nivel.nota.blank}")
    private String rangoNota;
}
