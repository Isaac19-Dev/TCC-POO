package com.parqueadero.api.base.exceptions; // Define el paquete base para excepciones globales
import java.time.LocalDateTime; // Manejo de tiempo
import java.util.*; // Colecciones
import org.springframework.http.*; // Manejo de estados HTTP
import org.springframework.web.bind.annotation.*; // Anotaciones web

@RestControllerAdvice // Esta anotación convierte esta clase en un interceptor global que captura cualquier excepción que se dispare en cualquier controlador
public class GlobalExceptionHandler { // Clase encargada de mapear excepciones Java a respuestas JSON limpias y amigables
    @ExceptionHandler(IllegalArgumentException.class) // Indica que este método va a capturar específicamente errores de tipo IllegalArgumentException
    public ResponseEntity<Map<String, Object>> handleBadArgs(IllegalArgumentException ex) { // Intercepta el error antes de que rompa el servidor
        Map<String, Object> body = new LinkedHashMap<>(); // Crea un mapa (JSON) ordenado para la respuesta
        body.put("timestamp", LocalDateTime.now()); // Inserta la hora del error
        body.put("status", HttpStatus.BAD_REQUEST.value()); // Inserta el código HTTP (400)
        body.put("error", HttpStatus.BAD_REQUEST.getReasonPhrase()); // Inserta el texto "Bad Request"
        body.put("message", ex.getMessage()); // Extrae e inserta el mensaje original del error que lanzamos en el servicio (ej. "Plaza ocupada")
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST); // Retorna todo el mapa empacado en una respuesta HTTP 400 Bad Request
    } // Cierre del método
} // Cierre de la clase
