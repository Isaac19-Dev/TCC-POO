package com.parqueadero.api.aplication.Controller;

import com.parqueadero.api.aplication.Entities.Vehiculo;
import com.parqueadero.api.aplication.Service.IVehiculoService;
import java.util.List;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador REST encargado de gestionar los vehículos (clientes).
 * Expone la API para el CRUD en la ruta "/vehiculos".
 */
@RestController 
@RequestMapping("/vehiculos")
public class VehiculoController {
    
    // Interfaz del servicio de vehículos
    private final IVehiculoService vs; 

    /**
     * Constructor para la inyección de dependencias.
     */
    public VehiculoController(IVehiculoService vs) { 
        this.vs = vs; 
    }

    /**
     * Obtiene la lista completa de vehículos registrados.
     */
    @GetMapping
    public ResponseEntity<List<Vehiculo>> listar() { 
        return ResponseEntity.ok(vs.listar()); 
    }

    /**
     * Registra un nuevo vehículo en la base de datos.
     */
    @PostMapping
    public ResponseEntity<Vehiculo> crear(@RequestBody Vehiculo v) { 
        // Retorna HTTP 201 Created junto con el vehículo guardado
        return new ResponseEntity<>(vs.crear(v), HttpStatus.CREATED); 
    }

    /**
     * Actualiza los datos de un vehículo existente.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Vehiculo> actualizar(@PathVariable Long id, @RequestBody Vehiculo v) { 
        // Retorna HTTP 200 OK con el vehículo actualizado
        return ResponseEntity.ok(vs.actualizar(id, v)); 
    }

    /**
     * Elimina un vehículo de la base de datos.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) { 
        // Ejecuta la eliminación
        vs.eliminar(id); 
        // Retorna HTTP 204 No Content indicando que se borró con éxito
        return ResponseEntity.noContent().build(); 
    }
}
