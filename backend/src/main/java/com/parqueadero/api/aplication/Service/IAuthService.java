package com.parqueadero.api.aplication.Service;
import com.parqueadero.api.aplication.Request.AuthRequest;
import com.parqueadero.api.aplication.DTO.AuthResponseDTO;
public interface IAuthService {
    AuthResponseDTO login(AuthRequest r);
}
