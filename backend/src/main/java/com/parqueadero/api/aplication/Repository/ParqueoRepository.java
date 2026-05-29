package com.parqueadero.api.aplication.Repository; // Paquete repositorio
import com.parqueadero.api.aplication.Entities.EstadoParqueo; // Importa Enum de Estado
import com.parqueadero.api.aplication.Entities.Parqueo; // Importa Entidad Parqueo
import java.util.List; // Utilidad de lista
import java.util.Optional; // Utilidad para manejar valores que pueden ser nulos de forma segura
import org.springframework.data.jpa.repository.JpaRepository; // Base JPA

public interface ParqueoRepository extends JpaRepository<Parqueo, Long> { // Repositorio que maneja el histórico de la tabla 'parqueos'
    Optional<Parqueo> findByVehiculoIdAndEstado(Long vehiculoId, EstadoParqueo estado); // Query automático de Spring que busca un parqueo buscando por el ID del vehiculo Y su estado actual (Retorna un Optional porque puede que no haya ninguno)
    List<Parqueo> findByEstado(EstadoParqueo estado); // Query automático que retorna todos los registros filtrando únicamente por la columna 'estado'
}
