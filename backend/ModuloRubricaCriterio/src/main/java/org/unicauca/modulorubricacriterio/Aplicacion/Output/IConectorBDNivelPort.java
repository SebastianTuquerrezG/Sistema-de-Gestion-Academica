package org.unicauca.modulorubricacriterio.Aplicacion.Output;

import java.util.List;

import org.unicauca.modulorubricacriterio.Dominio.Modelos.Nivel;

public interface IConectorBDNivelPort {
     List<Nivel> findAll();/*<!Consulta todos los niveles de la BD*/

     Nivel findById(Long id);/*<!Encuentra un nivel en específico */

     Nivel save(Nivel objNivel);/*<!Guarda un nivel*/

     Nivel update(Long id, Nivel objNivel);/*<!Actualiza un nivel*/
    
     Nivel delete(Long idNivel);/*<!Elimina un nivel*/
}
