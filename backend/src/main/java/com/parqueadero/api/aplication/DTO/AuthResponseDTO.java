package com.parqueadero.api.aplication.DTO;

/**
 * Record inmutable de transferencia de datos.
 * Sirve para enviar al frontend la respuesta de inicio de sesión de forma segura y estructurada como JSON.
 */
public record AuthResponseDTO(
    // Contiene el String del Token que el usuario usará en las siguientes peticiones
    String token
) {}
