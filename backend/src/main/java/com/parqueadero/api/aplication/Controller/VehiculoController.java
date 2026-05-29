package com.parqueadero.api.aplication.Controller; // Paquete de los controladores
import com.parqueadero.api.aplication.Entities.Vehiculo; // Importa entidad Vehiculo
import com.parqueadero.api.aplication.Service.IVehiculoService; // Importa interfaz de negocio para Vehiculos
import java.util.List; // Importa la interfaz List
import org.springframework.http.*; // Manejo HTTP (ResponseEntity, HttpStatus)
import org.springframework.web.bind.annotation.*; // Anotaciones REST (RestController, GetMapping, etc.)

@RestController @RequestMapping("/vehiculos") // Api REST expuesta en la ruta "/vehiculos"
public class VehiculoController { // Clase encargada de operaciones CRUD sobre vehículos
    private final IVehiculoService vs; // Dependencia de lógica de negocio (Servicio)
    public VehiculoController(IVehiculoService vs) { this.vs = vs; } // Constructor para inyectar el servicio

    @GetMapping // GET a "/vehiculos"
    public ResponseEntity<List<Vehiculo>> listar() { return ResponseEntity.ok(vs.listar()); } // Llama al servicio para obtener todos los vehículos y devuelve 200 OK

    @PostMapping // POST a "/vehiculos" para crear
    public ResponseEntity<Vehiculo> crear(@RequestBody Vehiculo v) { return new ResponseEntity<>(vs.crear(v), HttpStatus.CREATED); } // Pasa el objeto Vehiculo recibido en el body al servicio y devuelve 201 CREATED

    @PutMapping("/{id}") // PUT a "/vehiculos/{id}" para actualizar un vehículo existente
    public ResponseEntity<Vehiculo> actualizar(@PathVariable Long id, @RequestBody Vehiculo v) { return ResponseEntity.ok(vs.actualizar(id, v)); } // Actualiza usando el ID de la URL y los datos del body

    @DeleteMapping("/{id}") // DELETE a "/vehiculos/{id}" para eliminar
    public ResponseEntity<Void> eliminar(@PathVariable Long id) { vs.eliminar(id); return ResponseEntity.noContent().build(); } // Elimina y retorna código 204 No Content
}
