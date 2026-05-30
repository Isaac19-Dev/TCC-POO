package com.parqueadero.api.aplication.DTO;

import java.time.LocalDateTime;

// DTO (Data Transfer Object) para representar el estado de un espacio de parqueo.
// Se usa en el endpoint GET /parqueos/espacios para mostrar si cada espacio está libre u ocupado.
// Record de Java: inmutable, agrupa múltiples campos en un solo objeto de respuesta.
//
// Campos:
//   espacioId    → ID único del espacio en la base de datos
//   codigo       → Código visual del espacio (ej: "A1", "B3")
//   estado       → "LIBRE" o "OCUPADO"
//   parqueoId    → ID del parqueo activo (null si está libre)
//   vehiculoId   → ID del vehículo que lo ocupa (null si está libre)
//   placa        → Placa del vehículo que lo ocupa (null si está libre)
//   tipo         → Tipo del vehículo: "CARRO" o "MOTO" (null si está libre)
//   propietario  → Nombre del dueño del vehículo (null si está libre)
//   registradoPor→ Usuario que registró la entrada (null si está libre)
//   fechaEntrada → Fecha y hora de entrada del vehículo (null si está libre)
public record EspacioDTO(
    Long espacioId,
    String codigo,
    String estado,
    Long parqueoId,
    Long vehiculoId,
    String placa,
    String tipo,
    String propietario,
    String registradoPor,
    LocalDateTime fechaEntrada
) {}
