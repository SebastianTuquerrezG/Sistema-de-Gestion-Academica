package org.unicauca.modulorubricacriterio.Aplicación.Input;

import java.util.List;

import org.unicauca.modulorubricacriterio.Dominio.Modelos.Criterio;

public interface IGestionCriterioPort {
    // Métodos para gestionar criterios
    public List<Criterio> consultarCriterios();/*<!Consulta una lista de criterios*/

    public Criterio consultarCriterio(Long Id);/*<!Consulta criterio por id*/

    public Criterio crearCriterio(Criterio objPCriterio);/*<!Crea un criterio*/

    public Criterio modificarCriterio(Long Id, Criterio objPCriterio);/*<!Actualiza un criterio*/

    public Criterio eliminarCriterio(Long Id);/*<!Elimina un criterio*/
}
