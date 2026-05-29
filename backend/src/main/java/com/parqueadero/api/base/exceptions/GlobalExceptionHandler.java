package com.parqueadero.api.base.exceptions;

import java.time.LocalDateTime;
import java.util.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

/**
 * Interceptor global para atrapar excepciones en toda la aplicación.
 * Asegura que los errores devuelvan JSON limpios en lugar de páginas de error genéricas.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Atrapa las excepciones de tipo IllegalArgumentException.
     * Convierte el error en una respuesta HTTP 400 (Bad Request).
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleBadArgs(IllegalArgumentException ex) { 
        // Se usa LinkedHashMap para mantener el orden de las propiedades JSON
        Map<String, Object> body = new LinkedHashMap<>(); 
        
        // Atributos del JSON de error
        body.put("timestamp", LocalDateTime.now()); 
        body.put("status", HttpStatus.BAD_REQUEST.value()); 
        body.put("error", HttpStatus.BAD_REQUEST.getReasonPhrase()); 
        body.put("message", ex.getMessage()); 
        
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST); 
    }
}
