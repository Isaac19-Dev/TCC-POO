package com.parqueadero.api.aplication.DTO;

import java.time.LocalDateTime;

/**
 * Mega-objeto que viaja al frontend para dibujar la interfaz gráfica.
 * Fusiona los datos de un espacio físico (como 'A1') junto con los datos del ocupante (matrícula, nombre) si lo hubiera.
 */
public record EspacioDTO(
    // Identificador único del espacio
    Long espacioId, 
    // Nombre visible del espacio, ej. A1
    String codigo, 
    // Indicador si está ocupado o libre
    String estado, 
    // ID único del ticket o registro del servicio
    Long parqueoId, 
    // ID del vehículo que se parqueó
    Long vehiculoId, 
    // Matrícula del vehículo
    String placa, 
    // Clasificación del vehículo (carro o moto)
    String tipo, 
    // Nombre de quien conduce
    String propietario, 
    // Quien cobró o registró la entrada
    String registradoPor, 
    // Fecha y hora del momento exacto del ingreso
    LocalDateTime fechaEntrada
) {}
