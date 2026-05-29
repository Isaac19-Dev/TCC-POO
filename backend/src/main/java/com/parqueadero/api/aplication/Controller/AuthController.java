package com.parqueadero.api.aplication.Controller;

import com.parqueadero.api.aplication.Request.AuthRequest;
import com.parqueadero.api.aplication.DTO.AuthResponseDTO;
import com.parqueadero.api.aplication.Service.IAuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador REST encargado de la autenticación de usuarios.
 * Recibe peticiones HTTP en la ruta "/auth".
 */
@RestController 
@RequestMapping("/auth")
public class AuthController {
    
    // Dependencia del servicio de autenticación
    private final IAuthService as; 

    /**
     * Constructor que inyecta el servicio de autenticación.
     */
    public AuthController(IAuthService as) { 
        this.as = as; 
    }

    /**
     * Endpoint para iniciar sesión.
     * Recibe credenciales y devuelve un token JWT (simulado).
     */
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody AuthRequest req) { 
        // Llama al servicio y devuelve la respuesta envuelta en un HTTP 200 OK
        return ResponseEntity.ok(as.login(req)); 
    }
}
