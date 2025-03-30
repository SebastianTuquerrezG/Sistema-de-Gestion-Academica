package org.unicauca.modulorubricacriterio.exception.exceptionOwn;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.unicauca.modulorubricacriterio.exception.excepcionStructure.CodigoError;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public abstract class GestionModuloRuntimeException extends RuntimeException {

  protected CodigoError codigoError;
  public abstract String formatException();

}
