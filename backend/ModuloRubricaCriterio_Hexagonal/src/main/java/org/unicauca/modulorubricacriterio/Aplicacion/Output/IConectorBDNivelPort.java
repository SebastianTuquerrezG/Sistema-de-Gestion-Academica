package org.unicauca.modulorubricacriterio.Aplicacion.Output;

import java.util.List;

import org.unicauca.modulorubricacriterio.Dominio.Modelos.Nivel;

public interface IConectorBDNivelPort {
    public List<Nivel> findAll();/*<!Consulta todos los niveles de la BD*/

    public Nivel findById(Long id);/*<!Encuentra un nivel en específico */

    public Nivel save(Nivel objNivel);/*<!Guarda un nivel*/

    public Nivel update(Long id, Nivel objNivel);/*<!Actualiza un nivel*/
    
    public Nivel delete(Long idNivel);/*<!Elimina un nivel*/
}
