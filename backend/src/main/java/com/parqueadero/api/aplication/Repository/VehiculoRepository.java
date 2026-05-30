package com.parqueadero.api.aplication.Repository;

import com.parqueadero.api.aplication.Entities.Vehiculo;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

// Repositorio para la entidad Vehiculo.
// Hereda todos los métodos CRUD de JpaRepository automáticamente.
public interface VehiculoRepository extends JpaRepository<Vehiculo, Long> {

    // Busca un vehículo por su placa ignorando mayúsculas/minúsculas.
    // Spring traduce este método a: SELECT * FROM vehiculos WHERE UPPER(placa) = UPPER(?)
    // Retorna Optional porque el vehículo puede no existir.
    // Se usa para verificar que no se registre una placa duplicada (ej: "ABC123" y "abc123"
    // se tratarían como la misma placa).
    Optional<Vehiculo> findByPlacaIgnoreCase(String placa);
}
