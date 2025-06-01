package org.unicauca.modulorubricacriterio.Infraestructura.Input.estados;

import org.unicauca.modulorubricacriterio.Infraestructura.Input.validacionEstados.EstadosController;

public class EstadoInactivo implements IEstados{

    @Override
    public Resultado activar(EstadosController estadosController) {
        EstadoActivo estadoActivo = new EstadoActivo();
        estadosController.setEstado(estadoActivo);
        return new Resultado(true, "El estado ha sido activado correctamente");
    }

    @Override
    public Resultado desactivar(EstadosController estadosController) {
        return new Resultado(false, "El estado ya está Inactivo");
    }

}
