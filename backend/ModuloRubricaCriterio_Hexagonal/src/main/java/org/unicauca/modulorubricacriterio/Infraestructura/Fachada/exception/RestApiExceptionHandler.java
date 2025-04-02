package org.unicauca.modulorubricacriterio.Infraestructura.Fachada.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.unicauca.modulorubricacriterio.Infraestructura.Fachada.exception.excepcionStructure.CodigoError;
import org.unicauca.modulorubricacriterio.Infraestructura.Fachada.exception.excepcionStructure.Error;
import org.unicauca.modulorubricacriterio.Infraestructura.Fachada.exception.excepcionStructure.ErrorUtils;
import org.unicauca.modulorubricacriterio.Infraestructura.Fachada.exception.exceptionOwn.CambioInvalidoException;
import org.unicauca.modulorubricacriterio.Infraestructura.Fachada.exception.exceptionOwn.EntidadNoExisteException;
import org.unicauca.modulorubricacriterio.Infraestructura.Fachada.exception.exceptionOwn.EntidadYaExisteException;
import org.unicauca.modulorubricacriterio.Infraestructura.Fachada.exception.exceptionOwn.ReglaNegocioException;

import java.util.Locale;

@ControllerAdvice
public class RestApiExceptionHandler {

        @ExceptionHandler(Exception.class)
        public ResponseEntity<Error> handleGenericException(final HttpServletRequest req,
                                                        final Exception ex, final Locale locale) {
        final Error error = ErrorUtils
                .crearError(CodigoError.ERROR_GENERICO.getCodigo(),
                        CodigoError.ERROR_GENERICO.getLlaveMensaje(),
                        HttpStatus.INTERNAL_SERVER_ERROR.value())
                .setUrl(req.getRequestURL().toString()).setMetodo(req.getMethod());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        @ExceptionHandler(EntidadYaExisteException.class)
        public ResponseEntity<Error> handleGenericException(final HttpServletRequest req,
                                                        final EntidadYaExisteException ex) {
        final Error error = ErrorUtils
                .crearError(CodigoError.ENTIDAD_YA_EXISTE.getCodigo(),
                        String.format("%s, %s", CodigoError.ENTIDAD_YA_EXISTE.getLlaveMensaje(),
                                ex.getMessage()),
                        HttpStatus.NOT_ACCEPTABLE.value())
                .setUrl(req.getRequestURL().toString()).setMetodo(req.getMethod());
        return new ResponseEntity<>(error, HttpStatus.NOT_ACCEPTABLE);
        }

        @ExceptionHandler(ReglaNegocioException.class)
        public ResponseEntity<Error> handleGenericException(final HttpServletRequest req,
                                                        final ReglaNegocioException ex, final Locale locale) {
        final Error error = ErrorUtils
                .crearError(CodigoError.VIOLACION_REGLA_DE_NEGOCIO.getCodigo(), ex.formatException(),
                        HttpStatus.BAD_REQUEST.value())
                .setUrl(req.getRequestURL().toString()).setMetodo(req.getMethod());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

        @ExceptionHandler(EntidadNoExisteException.class)
        public ResponseEntity<Error> handleGenericException(final HttpServletRequest req,
                                                        final EntidadNoExisteException ex, final Locale locale) {
                final Error error = ErrorUtils
                        .crearError(CodigoError.ENTIDAD_NO_ENCONTRADA.getCodigo(),
                                String.format("%s, %s",
                                        CodigoError.ENTIDAD_NO_ENCONTRADA.getLlaveMensaje(),
                                        ex.getMessage()),
                                HttpStatus.NOT_FOUND.value())
                        .setUrl(req.getRequestURL().toString()).setMetodo(req.getMethod());
                return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        
        @ExceptionHandler(CambioInvalidoException.class)
        public ResponseEntity<Error> handleGenericException(final HttpServletRequest req,
                                                                final CambioInvalidoException ex, final Locale locale) {
                final Error error = ErrorUtils
                        .crearError(CodigoError.CAMBIO_INVALIDO.getCodigo(),
                                String.format("%s, %s",
                                        CodigoError.CAMBIO_INVALIDO.getLlaveMensaje(),
                                        ex.getMessage()),
                                HttpStatus.NOT_ACCEPTABLE.value())
                        .setUrl(req.getRequestURL().toString()).setMetodo(req.getMethod());
                return new ResponseEntity<>(error, HttpStatus.NOT_ACCEPTABLE);
        }
}
