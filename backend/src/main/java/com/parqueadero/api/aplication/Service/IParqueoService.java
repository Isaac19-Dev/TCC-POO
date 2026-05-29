package com.parqueadero.api.aplication.Service;

import com.parqueadero.api.aplication.Entities.Parqueo;
import com.parqueadero.api.aplication.DTO.EspacioDTO;
import java.util.List;

/**
 * Interfaz que define las operaciones de negocio para la gestión de parqueos.
 */
public interface IParqueoService {
    /**
     * Registra el ingreso de un vehículo.
     */
    Parqueo registrarEntrada(Long vehiculoId, Long espacioId, String registradoPor);
    
    /**
     * Registra la salida de un vehículo y realiza el cobro.
     */
    Parqueo registrarSalida(Long id);
    
    /**
     * Obtiene todos los registros de parqueo (historial).
     */
    List<Parqueo> listar();
    
    /**
     * Obtiene solo los parqueos que aún no han salido.
     */
    List<Parqueo> listarActivos();
    
    /**
     * Obtiene el listado de espacios fijos indicando cuáles están ocupados.
     */
    List<EspacioDTO> listarEspacios();
}
