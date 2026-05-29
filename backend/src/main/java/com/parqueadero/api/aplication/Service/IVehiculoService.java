package com.parqueadero.api.aplication.Service;

import com.parqueadero.api.aplication.Entities.Vehiculo;
import java.util.List;

/**
 * Interfaz que define las reglas para la administración de vehículos.
 */
public interface IVehiculoService {
    /**
     * Guarda un nuevo vehículo.
     */
    Vehiculo crear(Vehiculo v);
    
    /**
     * Obtiene el listado total de vehículos.
     */
    List<Vehiculo> listar();
    
    /**
     * Busca un vehículo específico por su identificador.
     */
    Vehiculo obtener(Long id);
    
    /**
     * Sobrescribe los datos de un vehículo existente.
     */
    Vehiculo actualizar(Long id, Vehiculo v);
    
    /**
     * Elimina un vehículo por su identificador.
     */
    void eliminar(Long id);
}
