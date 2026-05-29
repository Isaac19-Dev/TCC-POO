package com.parqueadero.api.aplication.Controller; // Paquete de los controladores
import com.parqueadero.api.aplication.Entities.Parqueo; // Importa la entidad Parqueo
import com.parqueadero.api.aplication.DTO.EspacioDTO; // Importa el DTO de espacios
import com.parqueadero.api.aplication.Request.EntradaReq; // Importa el objeto de petición para registrar entrada
import com.parqueadero.api.aplication.Service.IParqueoService; // Importa el servicio que contiene la lógica de negocio de parqueos
import java.util.List; // Importa List
import org.springframework.http.*; // Importa clases para manejo HTTP
import org.springframework.web.bind.annotation.*; // Importa anotaciones web (GET, POST, etc.)

@RestController @RequestMapping("/parqueos") // Define la clase como controlador REST en la ruta "/parqueos"
public class ParqueoController { // Controlador principal para gestionar ingresos y salidas de vehículos
    private final IParqueoService ps; // Servicio inyectado
    public ParqueoController(IParqueoService ps) { this.ps = ps; } // Constructor para inyectar IParqueoService

    @GetMapping // Endpoint GET a "/parqueos"
    public ResponseEntity<List<Parqueo>> listar() { return ResponseEntity.ok(ps.listar()); } // Devuelve todo el historial de parqueos
    
    @GetMapping("/activos") // Endpoint GET a "/parqueos/activos"
    public ResponseEntity<List<Parqueo>> listarActivos() { return ResponseEntity.ok(ps.listarActivos()); } // Devuelve solo los vehículos actualmente en el parqueadero
    
    @GetMapping("/espacios") // Endpoint GET a "/parqueos/espacios"
    public ResponseEntity<List<EspacioDTO>> listarEspacios() { return ResponseEntity.ok(ps.listarEspacios()); } // Devuelve el estado (libre/ocupado) de los 5 espacios fijos
    
    @PostMapping("/entrada") // Endpoint POST a "/parqueos/entrada"
    public ResponseEntity<Parqueo> entrada(@RequestBody EntradaReq req, @RequestHeader(value="Authorization", defaultValue="User") String user) { // Recibe el ID del vehículo, espacio, y el usuario desde los Headers HTTP
        return new ResponseEntity<>(ps.registrarEntrada(req.vehiculoId(), req.espacioId(), user), HttpStatus.CREATED); // Llama al servicio para registrar la entrada y devuelve 201 CREATED
    }
    
    @PostMapping("/{id}/salida") // Endpoint POST a "/parqueos/{id}/salida" donde {id} es una variable dinámica en la URL
    public ResponseEntity<Parqueo> salida(@PathVariable Long id) { return ResponseEntity.ok(ps.registrarSalida(id)); } // Registra la salida, calcula horas y costo total, devolviendo 200 OK
}
