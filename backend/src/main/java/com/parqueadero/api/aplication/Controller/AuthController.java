package com.parqueadero.api.aplication.Controller; // Paquete de los controladores (API REST)
import com.parqueadero.api.aplication.Request.AuthRequest; // Importa la estructura de la petición de login
import com.parqueadero.api.aplication.DTO.AuthResponseDTO; // Importa la estructura de la respuesta de login
import com.parqueadero.api.aplication.Service.IAuthService; // Importa la interfaz del servicio de autenticación
import org.springframework.http.ResponseEntity; // Importa la clase para estructurar las respuestas HTTP
import org.springframework.web.bind.annotation.*; // Importa anotaciones de controladores web (GET, POST, etc.)

@RestController @RequestMapping("/auth") // @RestController indica que es una API REST, @RequestMapping define la ruta base "/auth"
public class AuthController { // Clase que expone los endpoints de autenticación
    private final IAuthService as; // Dependencia al servicio de autenticación (inyección)
    public AuthController(IAuthService as) { this.as = as; } // Constructor donde Spring inyecta la implementación del servicio
    @PostMapping("/login") // Mapea las peticiones HTTP POST a la ruta "/auth/login"
    public ResponseEntity<AuthResponseDTO> login(@RequestBody AuthRequest req) { return ResponseEntity.ok(as.login(req)); } // Recibe credenciales, llama al servicio y devuelve 200 OK con el token
}
