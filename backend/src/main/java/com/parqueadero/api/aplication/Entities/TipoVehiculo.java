package com.parqueadero.api.aplication.Entities;

/**
 * Enumeración para clasificar los vehículos, lo cual afecta el cobro (tarifa).
 */
public enum TipoVehiculo { 
    // Vehículo tipo carro, paga más
    CARRO, 
    // Vehículo tipo motocicleta, paga menos
    MOTO 
}
