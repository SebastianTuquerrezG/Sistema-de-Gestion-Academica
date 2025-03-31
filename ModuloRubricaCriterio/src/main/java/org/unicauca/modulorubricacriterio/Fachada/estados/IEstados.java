package org.unicauca.modulorubricacriterio.Fachada.estados;

import org.unicauca.modulorubricacriterio.Fachada.validacionEstados.EstadosController;

public interface IEstados {
    Resultado activar(EstadosController estadosController);
    Resultado desactivar(EstadosController estadosController);
}
