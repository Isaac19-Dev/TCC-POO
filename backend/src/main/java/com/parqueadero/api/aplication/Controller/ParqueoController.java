package com.parqueadero.api.aplication.Controller;

import com.parqueadero.api.aplication.Entities.Parqueo;
import com.parqueadero.api.aplication.DTO.EspacioDTO;
import com.parqueadero.api.aplication.Request.EntradaReq;
import com.parqueadero.api.aplication.Service.IParqueoService;
import java.util.List;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

// Controlador REST para la gestión de parqueos (entradas, salidas y estado de espacios).
// @RestController → retorna JSON en todos los métodos.
// @RequestMapping("/parqueos") → base de todas las rutas: /parqueos
@RestController
@RequestMapping("/parqueos")
public class ParqueoController {

    // Servicio inyectado por constructor.
    private final IParqueoService ps;

    public ParqueoController(IParqueoService ps) {
        this.ps = ps;
    }

    // ── GET /parqueos ──
    // Retorna el historial completo de todos los parqueos (activos + finalizados).
    // HTTP 200 + arreglo JSON con todos los registros.
    @GetMapping
    public ResponseEntity<List<Parqueo>> listar() {
        return ResponseEntity.ok(ps.listar());
    }

    // ── GET /parqueos/activos ──
    // Retorna solo los parqueos con estado ACTIVO (vehículos dentro del parqueadero ahora mismo).
    // HTTP 200 + arreglo JSON de parqueos activos.
    @GetMapping("/activos")
    public ResponseEntity<List<Parqueo>> listarActivos() {
        return ResponseEntity.ok(ps.listarActivos());
    }

    // ── GET /parqueos/espacios ──
    // Retorna el estado de todos los espacios del parqueadero.
    // Cada espacio indica si está "LIBRE" u "OCUPADO" con el detalle del vehículo.
    // HTTP 200 + arreglo JSON de EspacioDTO.
    @GetMapping("/espacios")
    public ResponseEntity<List<EspacioDTO>> listarEspacios() {
        return ResponseEntity.ok(ps.listarEspacios());
    }

    // ── POST /parqueos/entrada ──
    // Registra la entrada de un vehículo al parqueadero.
    // @RequestBody EntradaReq req → body JSON con vehiculoId y espacioId (opcional).
    // @RequestHeader Authorization → Lee el token del header para extraer el nombre del usuario.
    //   Formato esperado: "Bearer fake-jwt-token-admin"
    //   Si no se envía el header, usa "User" como valor por defecto.
    // Retorna HTTP 201 (CREATED) con el parqueo creado.
    @PostMapping("/entrada")
    public ResponseEntity<Parqueo> entrada(
        @RequestBody EntradaReq req,
        @RequestHeader(value = "Authorization", defaultValue = "User") String authHeader
    ) {
        // Extrae el nombre del usuario del token del header Authorization.
        String username = "System"; // valor por defecto si el token no se puede leer

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            // Elimina el prefijo "Bearer " para obtener solo el token.
            String token = authHeader.substring(7);

            if (token.startsWith("fake-jwt-token-")) {
                // Extrae el nombre de usuario del token (lo que viene después de "fake-jwt-token-").
                username = token.substring("fake-jwt-token-".length());
                // El token del admin es "fake-jwt-token-admin" (porque en AuthServiceImpl
                // se construye como "fake-jwt-token-" + req.username()).
                if ("admin".equals(username)) username = "admin";
            } else {
                // Si el token no sigue el formato esperado, úsalo directamente como nombre.
                username = token;
            }
        }

        // Llama al servicio con el ID del vehículo, el ID del espacio y el nombre del usuario.
        return new ResponseEntity<>(ps.registrarEntrada(req.vehiculoId(), req.espacioId(), username), HttpStatus.CREATED);
    }

    // ── PUT /parqueos/salida/{id} ──
    // Registra la salida de un vehículo: calcula el tiempo y genera el cobro.
    // @PathVariable Long id → ID del parqueo activo a finalizar (tomado de la URL).
    // Retorna HTTP 200 con el parqueo finalizado (incluyendo horas cobradas y total).
    @PutMapping("/salida/{id}")
    public ResponseEntity<Parqueo> salida(@PathVariable Long id) {
        return ResponseEntity.ok(ps.registrarSalida(id));
    }
}
