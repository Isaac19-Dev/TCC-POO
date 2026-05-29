package com.parqueadero.api.aplication.Repository;

import com.parqueadero.api.aplication.Entities.Vehiculo;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repositorio de clientes (vehículos).
 */
public interface VehiculoRepository extends JpaRepository<Vehiculo, Long> {
    
    /**
     * Busca un cliente en base a la matrícula de su coche, ignorando mayúsculas.
     * Útil para validar duplicados o colisiones antes de guardar.
     */
    Optional<Vehiculo> findByPlacaIgnoreCase(String placa);
}
