package com.parqueadero.api.service;

import com.parqueadero.api.aplication.Service.impl.ParqueoServiceImpl;
import com.parqueadero.api.aplication.Repository.*;
import com.parqueadero.api.aplication.Service.IParqueoService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

/**
 * Clase de pruebas unitarias (Unit Tests) para el servicio de Parqueo.
 * Sirve para automatizar pruebas matemáticas sin tener que levantar todo el servidor.
 */
class ParqueoServiceTest {
    
    // Instancia del servicio con repositorios nulos (solo probaremos matemáticas simples)
    private final ParqueoServiceImpl svc = new ParqueoServiceImpl(null, null, null);

    /**
     * Prueba que si alguien se pasa por unos minutos (ej. 1h 10m), se le cobre la siguiente hora completa (2 horas).
     */
    @Test void redondeoArriba() {
        assertEquals(2L, svc.calcularHoras(LocalDateTime.of(2026,4,17,8,0), LocalDateTime.of(2026,4,17,9,10)));
    }
    
    /**
     * Prueba que si alguien está exactamente 1 hora, se le cobre exactamente 1 hora.
     */
    @Test void exactoUnaHora() {
        assertEquals(1L, svc.calcularHoras(LocalDateTime.of(2026,4,17,8,0), LocalDateTime.of(2026,4,17,9,0)));
    }
    
    /**
     * Prueba que si alguien está solo 1 minuto, el cobro mínimo sea siempre de 1 hora.
     */
    @Test void minimoUnaHora() {
        assertEquals(1L, svc.calcularHoras(LocalDateTime.of(2026,4,17,8,0), LocalDateTime.of(2026,4,17,8,1)));
    }
}
