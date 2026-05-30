package com.parqueadero.api.aplication.DTO;

// DTO (Data Transfer Object) de respuesta para el login.
// Record de Java: inmutable, solo contiene el token que se devuelve al cliente
// después de autenticarse correctamente.
// El cliente debe guardar este token y enviarlo en el header "Authorization: Bearer <token>"
// en las siguientes peticiones para identificarse.
public record AuthResponseDTO(String token) {}
