package org.unicauca.modulorubricacriterio.Aplicacion.Output;

import java.util.List;

import org.unicauca.modulorubricacriterio.Dominio.Modelos.Rubrica;

public interface IConectorBDRubricaPort {
     List<Rubrica> findAll();

     Rubrica findById(Long idRubrica);

     Rubrica saveRubric(Rubrica objRubrica);

     Rubrica updateRubric(Long id, Rubrica objRubrica);

     Rubrica deleteRubric(Long Id);

     Rubrica changeEstate(Long idRubrica, String estadoRubrica);
}
