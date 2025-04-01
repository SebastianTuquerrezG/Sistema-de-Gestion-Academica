package org.unicauca.modulorubricacriterio.Fachada.validacionEstados;

import org.unicauca.modulorubricacriterio.Fachada.estados.EstadoActivo;
import org.unicauca.modulorubricacriterio.Fachada.estados.EstadoInactivo;
import org.unicauca.modulorubricacriterio.Fachada.estados.IEstados;
import org.unicauca.modulorubricacriterio.Fachada.estados.Resultado;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EstadosController {

    private IEstados estado;

    public EstadosController(Integer estado) {
        switch (estado) {
            case 0:
                this.estado = new EstadoActivo();
                break;
        
            case 1:
                this.estado = new EstadoInactivo();
                break;
        }
    }
    
    public Resultado cambioEstado(String accion) 
    {
        Resultado objResultado = null;
        switch (accion) {
            case "ACTIVAR":
                objResultado = this.estado.activar(this);
                System.out.println(objResultado.mensaje());
            break;
            case "DESACTIVAR":
                objResultado = this.estado.desactivar(this);
                System.out.println(objResultado.mensaje());
                break;
        }
        return objResultado;
    }

}
