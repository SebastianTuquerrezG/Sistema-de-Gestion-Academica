package org.unicauca.modulorubricacriterio.Aplicacion.Input;

import java.util.List;

import org.unicauca.modulorubricacriterio.Dominio.Modelos.Criterio;

public interface IGestionCriterioPort {
    // Métodos para gestionar criterios
    List<Criterio> consultarCriterios();/*<!Consulta una lista de criterios*/

     Criterio consultarCriterio(Long Id);/*<!Consulta criterio por id*/

     Criterio crearCriterio(Criterio objPCriterio);/*<!Crea un criterio*/

     Criterio modificarCriterio(Long Id, Criterio objPCriterio);/*<!Actualiza un criterio*/

     Criterio eliminarCriterio(Long Id);/*<!Elimina un criterio*/
}
