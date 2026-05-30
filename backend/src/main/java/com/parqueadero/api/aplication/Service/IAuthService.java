package com.parqueadero.api.aplication.Service;

import com.parqueadero.api.aplication.Request.AuthRequest;
import com.parqueadero.api.aplication.DTO.AuthResponseDTO;

// Interfaz (contrato) del servicio de autenticación.
// Define QUÉ puede hacer el servicio, pero no CÓMO lo hace.
// La implementación real está en AuthServiceImpl.java.
// Usar interfaces permite desacoplar el controlador de la implementación concreta
// (principio de programación orientada a interfaces / SOLID).
public interface IAuthService {

    // Recibe las credenciales del usuario y retorna un token si son válidas.
    // Lanza RuntimeException si las credenciales son incorrectas.
    AuthResponseDTO login(AuthRequest req);
}
