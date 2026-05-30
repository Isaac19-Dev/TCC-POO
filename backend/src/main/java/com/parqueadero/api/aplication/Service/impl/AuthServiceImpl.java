package com.parqueadero.api.aplication.Service.impl;

import com.parqueadero.api.aplication.Service.IAuthService;
import com.parqueadero.api.aplication.Request.AuthRequest;
import com.parqueadero.api.aplication.DTO.AuthResponseDTO;
import org.springframework.stereotype.Service;

// @Service indica que esta clase es un componente de la capa de negocio.
// Spring la detecta automáticamente y la registra como un Bean (objeto gestionado por Spring).
// Implementa la interfaz IAuthService, cumpliendo con el contrato definido.
@Service
public class AuthServiceImpl implements IAuthService {

    // Método que valida las credenciales del usuario.
    @Override
    public AuthResponseDTO login(AuthRequest req) {
        // Lógica de autenticación simplificada (hardcodeada):
        // Solo acepta usuario "admin" con contraseña "admin123".
        // En un sistema real usaríamos una tabla de usuarios en BD con contraseñas cifradas (BCrypt).
        if ("admin".equals(req.username()) && "admin123".equals(req.password())) {
            // Si las credenciales son correctas, genera y retorna un "token" simulado.
            // El token se construye como "fake-jwt-token-" + nombre_de_usuario.
            // En producción se usaría un JWT real con firma criptográfica.
            return new AuthResponseDTO("fake-jwt-token-" + req.username());
        }
        // Si las credenciales son incorrectas, lanza una excepción.
        // El GlobalExceptionHandler captura esta excepción y devuelve HTTP 400 al cliente.
        throw new RuntimeException("Credenciales invalidas");
    }
}
