package com.parqueadero.api.aplication.Repository; // Paquete repositorio
import com.parqueadero.api.aplication.Entities.Espacio; // Importa Entidad
import org.springframework.data.jpa.repository.JpaRepository; // Interfaz base de JPA Spring
public interface EspacioRepository extends JpaRepository<Espacio, Long> {} // Interfaz que hereda de JpaRepository. Spring Boot la implementa automáticamente en memoria. Permite usar funciones como save(), findAll(), findById() sobre la tabla de 'espacios'.
