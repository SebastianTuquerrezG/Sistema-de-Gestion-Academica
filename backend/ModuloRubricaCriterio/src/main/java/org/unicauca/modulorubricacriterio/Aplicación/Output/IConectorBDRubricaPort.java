package org.unicauca.modulorubricacriterio.Aplicación.Output;

import java.util.List;

import org.unicauca.modulorubricacriterio.Dominio.Modelos.Rubrica;
import org.unicauca.modulorubricacriterio.Dominio.Modelos.Materia;

public interface IConectorBDRubricaPort {
    public List<Rubrica> findAll();

    public Rubrica findById(Long idRubrica);

    public Rubrica saveRubric(Rubrica objRubrica);

    public Rubrica updateRubric(Long id, Rubrica objRubrica);

    public Rubrica deleteRubric(Long Id);

    public Rubrica changeEstate(Long idRubrica, String estadoRubrica);

    public List<Materia> findAllMaterias();/*<!Consulta todas las materias disponibles */
}
