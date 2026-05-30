package com.parqueadero.api.base.exceptions;

import java.time.LocalDateTime;
import java.util.Map;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

// @RestControllerAdvice convierte esta clase en un manejador global de excepciones.
// Cualquier excepción que se lance en CUALQUIER controlador o servicio será capturada aquí.
// Evita que errores internos se muestren al cliente con stack traces feos.
// Centraliza el manejo de errores en un solo lugar (principio DRY).
@RestControllerAdvice
public class GlobalExceptionHandler {

    // Captura todas las excepciones de tipo IllegalArgumentException.
    // Estas son errores de validación de negocio (ej: placa duplicada, plaza ocupada).
    // Retorna HTTP 400 (BAD REQUEST) con un JSON que describe el error.
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleBad(IllegalArgumentException e) {
        return new ResponseEntity<>(
            Map.of(
                "message", e.getMessage(),   // mensaje legible del error
                "timestamp", LocalDateTime.now() // fecha/hora exacta del error
            ),
            HttpStatus.BAD_REQUEST // código HTTP 400
        );
    }

    // Captura cualquier otra excepción no controlada (errores internos inesperados).
    // Retorna HTTP 500 (INTERNAL SERVER ERROR) con un mensaje genérico.
    // No se expone el detalle interno del error por seguridad.
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleInternal(Exception e) {
        return new ResponseEntity<>(
            Map.of(
                "message", "Error interno",      // mensaje genérico (no expone detalles)
                "timestamp", LocalDateTime.now() // fecha/hora del error
            ),
            HttpStatus.INTERNAL_SERVER_ERROR // código HTTP 500
        );
    }
}
