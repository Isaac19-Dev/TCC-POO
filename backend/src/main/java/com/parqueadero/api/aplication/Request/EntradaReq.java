package com.parqueadero.api.aplication.Request;

/**
 * Objeto de petición para capturar los IDs que el frontend envía cuando quiere parquear un carro nuevo.
 */
public record EntradaReq(
    // ID primario del auto
    Long vehiculoId, 
    // Espacio escogido. Si viene null, el sistema escoge uno automático.
    Long espacioId
) {}
