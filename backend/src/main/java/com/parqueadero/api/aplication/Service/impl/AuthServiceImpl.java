package com.parqueadero.api.aplication.Service.impl; // Define el paquete de implementación de servicios
import com.parqueadero.api.aplication.Service.IAuthService; // Importa la interfaz del servicio
import com.parqueadero.api.aplication.Request.AuthRequest; // Importa la clase Request que contiene credenciales
import com.parqueadero.api.aplication.DTO.AuthResponseDTO; // Importa la clase DTO que devolverá el token
import org.springframework.stereotype.Service; // Importa la anotación @Service de Spring

@Service // Marca esta clase como un servicio gestionado por el contenedor de Spring (Bean)
public class AuthServiceImpl implements IAuthService { // Clase que implementa la lógica de negocio de la interfaz IAuthService
    @Override // Sobrescribe el método de la interfaz
    public AuthResponseDTO login(AuthRequest req) { // Recibe la petición con usuario y contraseña
        if ("admin".equals(req.username()) && "admin123".equals(req.password())) { // Verifica si las credenciales son admin/admin123 de forma hardcodeada (básica)
            return new AuthResponseDTO("fake-jwt-token-12345"); // Si es correcto, simula la generación de un token devolviendo un token estático falso
        } // Cierre del condicional
        throw new RuntimeException("Credenciales invalidas"); // Si no coincide, lanza un error de tiempo de ejecución interrumpiendo el flujo
    } // Cierre del método
} // Cierre de clase
