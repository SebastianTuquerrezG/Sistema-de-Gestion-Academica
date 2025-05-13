package org.unicauca.modulorubricacriterio.Infraestructura.Input.estados;

import org.unicauca.modulorubricacriterio.Infraestructura.Input.validacionEstados.EstadosController;

public interface IEstados {
    Resultado activar(EstadosController estadosController);
    Resultado desactivar(EstadosController estadosController);
}
