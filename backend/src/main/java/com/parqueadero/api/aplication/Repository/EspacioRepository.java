package com.parqueadero.api.aplication.Repository;

import com.parqueadero.api.aplication.Entities.Espacio;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repositorio de Spring Data JPA para la entidad Espacio.
 * Interfaz que provee todos los métodos SQL sin tener que escribirlos.
 */
public interface EspacioRepository extends JpaRepository<Espacio, Long> {
    // Al heredar de JpaRepository, obtiene funciones como save(), findAll(), etc.
}
