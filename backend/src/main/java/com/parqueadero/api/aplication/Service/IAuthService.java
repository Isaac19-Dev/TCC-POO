package com.parqueadero.api.aplication.Service;

import com.parqueadero.api.aplication.Request.AuthRequest;
import com.parqueadero.api.aplication.DTO.AuthResponseDTO;

/**
 * Interfaz que define las operaciones permitidas para la autenticación.
 */
public interface IAuthService {
    /**
     * Valida las credenciales de un usuario y devuelve un token.
     */
    AuthResponseDTO login(AuthRequest req);
}
