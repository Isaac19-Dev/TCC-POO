package com.parqueadero.api.aplication.Entities;

// Enum (enumeración) que representa los posibles estados de un registro de parqueo.
// ACTIVO: el vehículo está actualmente dentro del parqueadero.
// FINALIZADO: el vehículo ya salió, se calculó el tiempo y se cobró.
public enum EstadoParqueo {
    ACTIVO,
    FINALIZADO
}
