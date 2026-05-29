package com.parqueadero.api.aplication.DTO; // Paquete de transferencia
import java.time.LocalDateTime; // Importa manejo de tiempo
public record EspacioDTO(Long espacioId, String codigo, String estado, Long parqueoId, Long vehiculoId, String placa, String tipo, String propietario, String registradoPor, LocalDateTime fechaEntrada) {} // Mega objeto inmutable que consolida la información de un espacio con la información del vehículo ocupante para que el frontend lo pueda dibujar en la interfaz gráfica fácilmente.
