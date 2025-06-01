package org.unicauca.modulorubricacriterio.Aplicacion.Input;

import java.util.List;

import org.unicauca.modulorubricacriterio.Dominio.Modelos.Materia;
import org.unicauca.modulorubricacriterio.Dominio.Modelos.Rubrica;

public interface IGestionRubricaPort {
    // Métodos para gestionar rúbricas
     List<Rubrica> consultarRubricas();/*<!Consulta una lista de rúbricas*/

     Rubrica consultarRubrica(Long Id);/*<!Consulta rúbrica por id*/

     Rubrica crearRubrica(Rubrica objPRubrica);/*<!Crea una rúbrica*/

     Rubrica modificarRubrica(Long Id, Rubrica objPRubrica);/*<!Actualiza una rúbrica*/

     Rubrica editarEstadoRubrica(Long id, String objPRubrica);/*<!Trata de cambiar el estado de una rúbrica de ACTIVO a INACTIVO y viceversa*/
    
     Rubrica eliminarRubrica(Long Id);/*<!Elimina una rúbrica*/

     List<Materia> consultarMaterias();/*<!Consulta una lista de materias*/
}
