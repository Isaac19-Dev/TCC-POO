package com.parqueadero.api.aplication.Service;

import com.parqueadero.api.aplication.Entities.Parqueo;
import com.parqueadero.api.aplication.DTO.EspacioDTO;
import java.util.List;

// Interfaz (contrato) del servicio de parqueos.
// Define todas las operaciones de negocio relacionadas con el estacionamiento.
// La implementación está en ParqueoServiceImpl.java.
public interface IParqueoService {

    // Retorna el historial completo de todos los parqueos (activos y finalizados).
    List<Parqueo> listar();

    // Retorna solo los parqueos con estado ACTIVO (vehículos actualmente dentro).
    List<Parqueo> listarActivos();

    // Retorna el estado de todos los espacios: cuáles están libres y cuáles ocupados,
    // con información del vehículo que los ocupa si están ocupados.
    List<EspacioDTO> listarEspacios();

    // Registra la entrada de un vehículo al parqueadero.
    // vid → ID del vehículo
    // eid → ID del espacio (puede ser null para asignación automática)
    // by  → nombre del usuario que registra la entrada
    Parqueo registrarEntrada(Long vid, Long eid, String by);

    // Registra la salida del vehículo, calcula el tiempo y cobra la tarifa.
    // id → ID del parqueo activo a finalizar
    Parqueo registrarSalida(Long id);
}
