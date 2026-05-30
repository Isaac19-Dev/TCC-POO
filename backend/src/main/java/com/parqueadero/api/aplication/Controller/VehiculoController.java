package com.parqueadero.api.aplication.Controller;

import com.parqueadero.api.aplication.Entities.Vehiculo;
import com.parqueadero.api.aplication.Service.IVehiculoService;
import java.util.List;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

// Controlador REST para la gestión de vehículos.
// @RestController → retorna JSON en todos los métodos.
// @RequestMapping("/vehiculos") → base de todas las rutas: /vehiculos
@RestController
@RequestMapping("/vehiculos")
public class VehiculoController {

    // Servicio inyectado por constructor.
    private final IVehiculoService vs;

    public VehiculoController(IVehiculoService vs) {
        this.vs = vs;
    }

    // ── GET /vehiculos ──
    // Lista todos los vehículos registrados en el sistema.
    // Retorna HTTP 200 con un arreglo JSON de vehículos.
    @GetMapping
    public ResponseEntity<List<Vehiculo>> listar() {
        return ResponseEntity.ok(vs.listar());
    }

    // ── POST /vehiculos ──
    // Registra un nuevo vehículo en el sistema.
    // @RequestBody → lee el JSON del body y lo convierte al objeto Vehiculo.
    // Retorna HTTP 201 (CREATED) con el vehículo guardado (incluyendo su nuevo ID).
    @PostMapping
    public ResponseEntity<Vehiculo> crear(@RequestBody Vehiculo v) {
        return new ResponseEntity<>(vs.crear(v), HttpStatus.CREATED);
    }

    // ── PUT /vehiculos/{id} ──
    // Actualiza los datos de un vehículo existente.
    // @PathVariable Long id → captura el ID de la URL (ej: /vehiculos/5).
    // @RequestBody Vehiculo v → los nuevos datos del vehículo vienen en el body JSON.
    // Retorna HTTP 200 con el vehículo actualizado.
    @PutMapping("/{id}")
    public ResponseEntity<Vehiculo> actualizar(@PathVariable Long id, @RequestBody Vehiculo v) {
        return ResponseEntity.ok(vs.actualizar(id, v));
    }

    // ── DELETE /vehiculos/{id} ──
    // Elimina un vehículo del sistema por su ID.
    // @PathVariable Long id → ID del vehículo a eliminar, tomado de la URL.
    // Retorna HTTP 204 (NO CONTENT) sin body si se eliminó correctamente.
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        vs.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
