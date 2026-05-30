package com.parqueadero.api.aplication.Request;

// Record de Java (inmutable): contiene los datos necesarios para registrar la entrada
// de un vehículo al parqueadero.
// Campos:
//   vehiculoId → ID del vehículo que va a ingresar (obligatorio)
//   espacioId  → ID del espacio específico donde se ubicará (puede ser null;
//                si es null, el sistema asigna automáticamente el primer espacio libre)
public record EntradaReq(Long vehiculoId, Long espacioId) {}
