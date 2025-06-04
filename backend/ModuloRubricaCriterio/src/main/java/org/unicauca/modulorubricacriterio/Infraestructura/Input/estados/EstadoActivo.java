package org.unicauca.modulorubricacriterio.Infraestructura.Input.estados;

import org.unicauca.modulorubricacriterio.Infraestructura.Input.validacionEstados.EstadosController;

public class EstadoActivo implements IEstados{

    @Override
    public Resultado activar(EstadosController estadosController) {
        return new Resultado(false, "El estado ya está activo");
    }

    @Override
    public Resultado desactivar(EstadosController estadosController) {
        EstadoInactivo estadoInactivo = new EstadoInactivo();
        estadosController.setEstado(estadoInactivo);
        return new Resultado(true, "El estado ha sido desactivado correctamente");
    }

    
}


