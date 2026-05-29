package com.parqueadero.api.aplication.DTO; // Paquete para objetos de transferencia de datos
public record AuthResponseDTO(String token) {} // 'record' es una estructura inmutable en Java. Aquí sirve para empaquetar el string de 'token' y enviarlo como un JSON plano ({"token": "..."}) al cliente.
