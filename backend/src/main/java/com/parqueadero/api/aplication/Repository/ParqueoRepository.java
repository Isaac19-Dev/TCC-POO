package com.parqueadero.api.aplication.Repository;

import com.parqueadero.api.aplication.Entities.*;
import java.util.*;
import org.springframework.data.jpa.repository.JpaRepository;

// Repositorio para la entidad Parqueo (sesiones de entrada/salida de vehículos).
// Hereda todos los métodos CRUD de JpaRepository automáticamente.
public interface ParqueoRepository extends JpaRepository<Parqueo, Long> {

    // Busca un parqueo activo específico de un vehículo.
    // Spring genera el SQL: SELECT * FROM parqueos WHERE vehiculo_id = ? AND estado = ?
    // Retorna Optional porque puede no existir (el vehículo podría no estar parqueado).
    // Se usa para evitar que un vehículo tenga dos parqueos activos al mismo tiempo.
    Optional<Parqueo> findByVehiculoIdAndEstado(Long vid, EstadoParqueo est);

    // Devuelve todos los parqueos con un estado específico (ACTIVO o FINALIZADO).
    // SQL generado: SELECT * FROM parqueos WHERE estado = ?
    // Se usa para listar todos los vehículos actualmente dentro del parqueadero.
    List<Parqueo> findByEstado(EstadoParqueo est);
}
