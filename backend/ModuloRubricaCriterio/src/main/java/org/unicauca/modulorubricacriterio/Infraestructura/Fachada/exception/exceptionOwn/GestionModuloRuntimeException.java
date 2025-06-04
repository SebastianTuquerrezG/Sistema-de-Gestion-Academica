package org.unicauca.modulorubricacriterio.Infraestructura.Fachada.exception.exceptionOwn;

import org.unicauca.modulorubricacriterio.Infraestructura.Fachada.exception.excepcionStructure.CodigoError;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public abstract class GestionModuloRuntimeException extends RuntimeException {

  protected CodigoError codigoError;
  public abstract String formatException();

}
