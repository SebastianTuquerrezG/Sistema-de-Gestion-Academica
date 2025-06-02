package org.unicauca.modulorubricacriterio.Dominio.Modelos;

import java.util.List;

import lombok.NoArgsConstructor;
import org.unicauca.modulorubricacriterio.Infraestructura.Input.validacionEstados.EstadosEnum;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Rubrica {
    private Long idRubrica;
    private Long idMateria;
    private String nombreRubrica;
    private String nombreMateria;
    private int notaRubrica;
    private String objetivoEstudio;
    List<Criterio> criterios;
    private EstadosEnum estado;
    private Long raId;
}
