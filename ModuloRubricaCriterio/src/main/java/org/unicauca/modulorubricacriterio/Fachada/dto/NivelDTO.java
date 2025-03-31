package org.unicauca.modulorubricacriterio.Fachada.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NivelDTO {
    private Long idNivel;
    private Long id_criterio;
    private String nivelDescripcion;
    private String rangoNota;
}
