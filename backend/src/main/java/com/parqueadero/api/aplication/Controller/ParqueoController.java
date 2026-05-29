package com.parqueadero.api.aplication.Controller;

import com.parqueadero.api.aplication.Entities.Parqueo;
import com.parqueadero.api.aplication.DTO.EspacioDTO;
import com.parqueadero.api.aplication.Request.EntradaReq;
import com.parqueadero.api.aplication.Service.IParqueoService;
import java.util.List;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador REST encargado de gestionar los ingresos y salidas del parqueadero.
 * Expone la API en la ruta "/parqueos".
 */
@RestController 
@RequestMapping("/parqueos")
public class ParqueoController {
    
    // Dependencia que contiene la lógica del negocio
    private final IParqueoService ps; 

    /**
     * Constructor que inyecta el servicio de parqueo.
     */
    public ParqueoController(IParqueoService ps) { 
        this.ps = ps; 
    }

    /**
     * Devuelve el historial completo de todos los parqueos registrados.
     */
    @GetMapping
    public ResponseEntity<List<Parqueo>> listar() { 
        // Retorna HTTP 200 OK con la lista de parqueos
        return ResponseEntity.ok(ps.listar()); 
    }
    
    /**
     * Devuelve únicamente los parqueos que están activos en este momento (sin salir).
     */
    @GetMapping("/activos")
    public ResponseEntity<List<Parqueo>> listarActivos() { 
        return ResponseEntity.ok(ps.listarActivos()); 
    }
    
    /**
     * Devuelve la lista de los 5 espacios fijos indicando si están libres u ocupados.
     */
    @GetMapping("/espacios")
    public ResponseEntity<List<EspacioDTO>> listarEspacios() { 
        return ResponseEntity.ok(ps.listarEspacios()); 
    }
    
    /**
     * Registra la entrada de un vehículo al parqueadero.
     */
    @PostMapping("/entrada")
    public ResponseEntity<Parqueo> entrada(@RequestBody EntradaReq req, @RequestHeader(value="Authorization", defaultValue="User") String user) { 
        // Llama al servicio con los datos y devuelve HTTP 201 Created
        return new ResponseEntity<>(ps.registrarEntrada(req.vehiculoId(), req.espacioId(), user), HttpStatus.CREATED); 
    }
    
    /**
     * Registra la salida de un vehículo, calculando el total a pagar.
     */
    @PostMapping("/{id}/salida")
    public ResponseEntity<Parqueo> salida(@PathVariable Long id) { 
        return ResponseEntity.ok(ps.registrarSalida(id)); 
    }
}
