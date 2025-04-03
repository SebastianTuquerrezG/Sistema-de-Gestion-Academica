package org.unicauca.modulorubricacriterio.Dominio.Modelos;

import java.util.List;

import org.unicauca.modulorubricacriterio.Infraestructura.Input.validacionEstados.EstadosEnum;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Rubrica {
    private Long idRubrica;
    private String nombreRubrica;
    private String materia;
    private int notaRubrica;
    private String objetivoEstudio;
    List<Criterio> criterios;
    private EstadosEnum estado;

    /*Constructor no parametrizado
    usar para deserialización de datos*/
    public Rubrica(){}

}
