package org.unicauca.modulorubricacriterio.Dominio.Modelos;
import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class Materia {
    public Long idMateria;
    public String name;

    public Materia(){  }
}
