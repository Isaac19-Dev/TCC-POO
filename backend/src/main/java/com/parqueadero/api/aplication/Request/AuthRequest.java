package com.parqueadero.api.aplication.Request;

/**
 * Molde inmutable que sirve para capturar las credenciales cuando el frontend hace una petición POST de Login.
 */
public record AuthRequest(
    // Nombre del usuario que intenta loguearse
    String username, 
    // Contraseña digitada
    String password
) {}
