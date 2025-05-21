package org.unicauca.modulorubricacriterio.Aplicacion.Input;

import java.util.List;

import org.unicauca.modulorubricacriterio.Dominio.Modelos.Nivel;

public interface IGestionNivelPort {
    // Métodos para gestionar niveles
     List<Nivel> consultarNiveles();/*<!Consulta una lista de niveles*/

     Nivel consultarNivel(Long Id);/*<!Consulta nivel por id*/

     Nivel crearNivel(Nivel objPNivel);/*<!Crea un nivel*/

     Nivel modificarNivel(Long Id, Nivel objPNivel);/*<!Actualiza un nivel*/

     Nivel eliminarNivel(Long Id);/*<!Elimina un nivel*/

}
