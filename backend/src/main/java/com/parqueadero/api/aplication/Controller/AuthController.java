package com.parqueadero.api.aplication.Controller;

import com.parqueadero.api.aplication.Request.AuthRequest;
import com.parqueadero.api.aplication.DTO.AuthResponseDTO;
import com.parqueadero.api.aplication.Service.IAuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// @RestController indica que esta clase es un controlador REST.
// Combina @Controller + @ResponseBody: todos los métodos retornan JSON automáticamente.
// @RequestMapping("/auth") → todas las rutas de este controlador empiezan con /auth
@RestController
@RequestMapping("/auth")
public class AuthController {

    // Servicio de autenticación inyectado por constructor.
    // Se usa la interfaz (no la implementación) para desacoplar capas.
    private final IAuthService as;

    public AuthController(IAuthService as) {
        this.as = as;
    }

    // ── POST /auth/login ──
    // Endpoint para que el usuario inicie sesión.
    // @PostMapping → solo acepta peticiones HTTP POST.
    // @RequestBody → deserializa el JSON del body a un objeto AuthRequest.
    // Retorna HTTP 200 con el token si las credenciales son correctas.
    // Retorna HTTP 400 si son incorrectas (el GlobalExceptionHandler lo maneja).
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody AuthRequest req) {
        return ResponseEntity.ok(as.login(req));
    }
}
