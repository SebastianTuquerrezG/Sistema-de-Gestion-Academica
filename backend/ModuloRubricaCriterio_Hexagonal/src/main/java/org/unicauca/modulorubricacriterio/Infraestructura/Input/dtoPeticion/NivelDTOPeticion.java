package org.unicauca.modulorubricacriterio.Infraestructura.Input.dtoPeticion;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
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
    @Pattern(regexp = "^(?:[0-4](?:\\.\\d{1,2})?|5(?:\\.0{1,2})?)$", message = "{Nivel.rangoNota}")
    private String rangoNota;
}
