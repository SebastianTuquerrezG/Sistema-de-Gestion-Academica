package org.unicauca.modulorubricacriterio.Fachada.exception.exceptionOwn;

import org.unicauca.modulorubricacriterio.Fachada.exception.excepcionStructure.CodigoError;

public class ReglaNegocioException extends GestionModuloRuntimeException {

  private static final String FORMATO_EXCEPCION = "%s - Violación a regla de negocio: %s";

  private final String reglaNegocio;

  public ReglaNegocioException(final String reglaNegocio) {
    super(CodigoError.VIOLACION_REGLA_DE_NEGOCIO);
    this.reglaNegocio = reglaNegocio;
  }

  @Override
  public String formatException() {
    return String.format(FORMATO_EXCEPCION, codigoError.getCodigo(), reglaNegocio);
  }
}
