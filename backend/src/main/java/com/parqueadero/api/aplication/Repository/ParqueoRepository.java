package com.parqueadero.api.aplication.Repository;

import com.parqueadero.api.aplication.Entities.EstadoParqueo;
import com.parqueadero.api.aplication.Entities.Parqueo;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repositorio para consultar el historial de parqueos.
 */
public interface ParqueoRepository extends JpaRepository<Parqueo, Long> {
    
    /**
     * Busca en la base de datos si un vehículo tiene un registro de parqueo específico activo.
     * Es equivalente a hacer un "SELECT * FROM parqueos WHERE vehiculo_id = X AND estado = Y".
     */
    Optional<Parqueo> findByVehiculoIdAndEstado(Long vehiculoId, EstadoParqueo estado);
    
    /**
     * Trae una lista filtrando por la columna estado.
     */
    List<Parqueo> findByEstado(EstadoParqueo estado);
}
