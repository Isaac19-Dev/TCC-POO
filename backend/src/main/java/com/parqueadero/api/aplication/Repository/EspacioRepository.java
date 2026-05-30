package com.parqueadero.api.aplication.Repository;

import com.parqueadero.api.aplication.Entities.Espacio;
import org.springframework.data.jpa.repository.JpaRepository;

// Repositorio para la entidad Espacio.
// Al extender JpaRepository, Spring genera automáticamente todos los métodos CRUD:
// save(), findById(), findAll(), deleteById(), count(), etc.
// No necesita implementación manual: Spring lo hace en tiempo de ejecución.
// Parámetros de JpaRepository: <Entidad, TipoDelId> → <Espacio, Long>
public interface EspacioRepository extends JpaRepository<Espacio, Long> {
    // No se necesitan consultas adicionales para los espacios.
    // Con findAll() y count() es suficiente para este módulo.
}
