package org.unicauca.modulorubricacriterio.Infraestructura.Fachada.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.unicauca.modulorubricacriterio.Infraestructura.Fachada.exception.excepcionStructure.CodigoError;
import org.unicauca.modulorubricacriterio.Infraestructura.Fachada.exception.excepcionStructure.Error;
import org.unicauca.modulorubricacriterio.Infraestructura.Fachada.exception.excepcionStructure.ErrorUtils;
import org.unicauca.modulorubricacriterio.Infraestructura.Fachada.exception.exceptionOwn.CambioInvalidoException;
import org.unicauca.modulorubricacriterio.Infraestructura.Fachada.exception.exceptionOwn.EntidadNoExisteException;
import org.unicauca.modulorubricacriterio.Infraestructura.Fachada.exception.exceptionOwn.EntidadYaExisteException;
import org.unicauca.modulorubricacriterio.Infraestructura.Fachada.exception.exceptionOwn.ReglaNegocioException;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class RestApiExceptionHandler {

        @ExceptionHandler(Exception.class)
        public ResponseEntity<Error> handleGenericException(final HttpServletRequest req) {
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
                                                        final ReglaNegocioException ex) {
        final Error error = ErrorUtils
                .crearError(CodigoError.VIOLACION_REGLA_DE_NEGOCIO.getCodigo(), ex.formatException(),
                        HttpStatus.BAD_REQUEST.value())
                .setUrl(req.getRequestURL().toString()).setMetodo(req.getMethod());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

        @ExceptionHandler(EntidadNoExisteException.class)
        public ResponseEntity<Error> handleGenericException(final HttpServletRequest req,
                                                        final EntidadNoExisteException ex) {
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
                                                                final CambioInvalidoException ex) {
                final Error error = ErrorUtils
                        .crearError(CodigoError.CAMBIO_INVALIDO.getCodigo(),
                                String.format("%s, %s",
                                        CodigoError.CAMBIO_INVALIDO.getLlaveMensaje(),
                                        ex.getMessage()),
                                HttpStatus.NOT_ACCEPTABLE.value())
                        .setUrl(req.getRequestURL().toString()).setMetodo(req.getMethod());
                return new ResponseEntity<>(error, HttpStatus.NOT_ACCEPTABLE);
        }

        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
                System.out.println("Retornando respuesta con los errores identificados");
                Map<String, String> errores = new HashMap<>();
                ex.getBindingResult().getAllErrors().forEach((error) -> {
                        String campo = ((FieldError) error).getField();
                        String mensajeDeError = error.getDefaultMessage();
                        errores.put(campo, mensajeDeError);
                });

                return new ResponseEntity<>(errores, HttpStatus.BAD_REQUEST);
        }

        
        @ResponseStatus(HttpStatus.BAD_REQUEST)
        @ExceptionHandler(ConstraintViolationException.class)
        ResponseEntity<String> handleConstraintViolationException(ConstraintViolationException e) {
                return new ResponseEntity<>(e.getMessage(),
                                HttpStatus.BAD_REQUEST);
        }
}
