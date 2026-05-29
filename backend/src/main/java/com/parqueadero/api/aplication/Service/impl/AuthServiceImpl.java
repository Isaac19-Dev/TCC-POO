package com.parqueadero.api.aplication.Service.impl;

import com.parqueadero.api.aplication.Service.IAuthService;
import com.parqueadero.api.aplication.Request.AuthRequest;
import com.parqueadero.api.aplication.DTO.AuthResponseDTO;
import org.springframework.stereotype.Service;

/**
 * Implementación de la lógica de autenticación.
 */
@Service
public class AuthServiceImpl implements IAuthService {
    
    /**
     * Valida si el usuario y la contraseña son "admin" y "admin123".
     */
    @Override
    public AuthResponseDTO login(AuthRequest req) { 
        // Compara las credenciales (lógica estática/hardcodeada)
        if ("admin".equals(req.username()) && "admin123".equals(req.password())) { 
            // Si es correcto, devuelve un token inventado
            return new AuthResponseDTO("fake-jwt-token-12345"); 
        } 
        
        // Lanza error 400 si la clave es incorrecta
        throw new RuntimeException("Credenciales invalidas"); 
    }
}
