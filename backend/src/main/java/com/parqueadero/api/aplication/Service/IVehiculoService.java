package com.parqueadero.api.aplication.Service;

import com.parqueadero.api.aplication.Entities.Vehiculo;
import java.util.List;

// Interfaz (contrato) del servicio de vehículos.
// Define todas las operaciones disponibles sobre la entidad Vehiculo.
// La implementación está en VehiculoServiceImpl.java.
public interface IVehiculoService {

    // Retorna la lista completa de todos los vehículos registrados en el sistema.
    List<Vehiculo> listar();

    // Crea y guarda un nuevo vehículo. Valida que la placa no esté duplicada.
    Vehiculo crear(Vehiculo v);

    // Busca un vehículo por su ID. Lanza excepción si no existe.
    Vehiculo obtener(Long id);

    // Actualiza los datos de un vehículo existente (placa, tipo, propietario).
    // Valida que la nueva placa no pertenezca a otro vehículo diferente.
    Vehiculo actualizar(Long id, Vehiculo v);

    // Elimina permanentemente un vehículo de la base de datos por su ID.
    void eliminar(Long id);
}
