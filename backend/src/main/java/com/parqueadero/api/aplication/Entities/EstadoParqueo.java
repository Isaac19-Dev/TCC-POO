package com.parqueadero.api.aplication.Entities;

/**
 * Enumeración que controla los estados del ciclo de vida de un servicio de parqueo.
 */
public enum EstadoParqueo { 
    // El vehículo sigue dentro del parqueadero
    ACTIVO, 
    // El vehículo ya pagó y se retiró del parqueadero
    FINALIZADO 
}
