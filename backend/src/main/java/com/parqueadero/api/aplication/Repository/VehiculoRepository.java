package com.parqueadero.api.aplication.Repository; // Paquete repositorio
import com.parqueadero.api.aplication.Entities.Vehiculo; // Importa Entidad Vehiculo
import java.util.Optional; // Utilidad de nulos
import org.springframework.data.jpa.repository.JpaRepository; // Base JPA

public interface VehiculoRepository extends JpaRepository<Vehiculo, Long> { // Repositorio que se encarga de guardar y buscar en la tabla 'vehiculos'
    Optional<Vehiculo> findByPlacaIgnoreCase(String placa); // Query automático que busca un vehículo pasando una placa, ignorando si son mayúsculas o minúsculas (IgnoreCase). Retorna Optional.
}
