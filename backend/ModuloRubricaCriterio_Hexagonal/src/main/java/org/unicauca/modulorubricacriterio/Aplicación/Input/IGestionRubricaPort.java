package org.unicauca.modulorubricacriterio.Aplicación.Input;

import java.util.List;

import org.unicauca.modulorubricacriterio.Dominio.Modelos.Rubrica;

public interface IGestionRubricaPort {
    // Métodos para gestionar rúbricas
    public List<Rubrica> consultarRubricas();/*<!Consulta una lista de rúbricas*/

    public Rubrica consultarRubrica(Long Id);/*<!Consulta rúbrica por id*/

    public Rubrica crearRubrica(Rubrica objPRubrica);/*<!Crea una rúbrica*/

    public Rubrica modificarRubrica(Long Id, Rubrica objPRubrica);/*<!Actualiza una rúbrica*/

    public Rubrica editarEstadoRubrica(Long id, String objPRubrica);/*<!Trata de cambiar el estado de una rúbrica de ACTIVO a INACTIVO y viceversa*/
    
    public Rubrica eliminarRubrica(Long Id);/*<!Elimina una rúbrica*/
}
