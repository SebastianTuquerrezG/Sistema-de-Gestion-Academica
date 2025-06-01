package org.unicauca.modulorubricacriterio.Infraestructura.Fachada.exception.exceptionOwn;

import org.unicauca.modulorubricacriterio.Infraestructura.Fachada.exception.excepcionStructure.CodigoError;

import lombok.Getter;

@Getter
public class CambioInvalidoException extends RuntimeException {
    private final String llaveMensaje;
    private final String codigo;

    public CambioInvalidoException(String message) {
        super(message);
        this.llaveMensaje = CodigoError.CAMBIO_INVALIDO.getLlaveMensaje();
        this.codigo = CodigoError.CAMBIO_INVALIDO.getCodigo();
    }

    public CambioInvalidoException(CodigoError code) {
        super(code.getCodigo());
        this.llaveMensaje = code.getLlaveMensaje();
        this.codigo = code.getCodigo();
    }

}
