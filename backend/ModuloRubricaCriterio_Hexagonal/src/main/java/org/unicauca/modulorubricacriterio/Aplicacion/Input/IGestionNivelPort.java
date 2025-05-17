package org.unicauca.modulorubricacriterio.Aplicacion.Input;

import java.util.List;

import org.unicauca.modulorubricacriterio.Dominio.Modelos.Nivel;

public interface IGestionNivelPort {
    // Métodos para gestionar niveles
    public List<Nivel> consultarNiveles();/*<!Consulta una lista de niveles*/

    public Nivel consultarNivel(Long Id);/*<!Consulta nivel por id*/

    public Nivel crearNivel(Nivel objPNivel);/*<!Crea un nivel*/

    public Nivel modificarNivel(Long Id, Nivel objPNivel);/*<!Actualiza un nivel*/

    public Nivel eliminarNivel(Long Id);/*<!Elimina un nivel*/

}
