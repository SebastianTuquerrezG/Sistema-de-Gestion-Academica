package org.unicauca.modulorubricacriterio.Fachada.estados;

/**
 * @boolean cambioPermitido: Indica si el cambio de estado es permitido o no.
 * @String mensaje: Mensaje que describe el resultado del cambio de estado.
 */

public record Resultado(boolean cambioPermitido, String mensaje) {

}
