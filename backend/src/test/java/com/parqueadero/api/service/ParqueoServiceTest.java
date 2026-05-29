package com.parqueadero.api.service;
import com.parqueadero.api.aplication.Service.impl.ParqueoServiceImpl;
import com.parqueadero.api.aplication.Repository.*;
import com.parqueadero.api.aplication.Service.IParqueoService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

class ParqueoServiceTest {
    private final ParqueoServiceImpl svc = new ParqueoServiceImpl(null, null, null);

    @Test void redondeoArriba() {
        assertEquals(2L, svc.calcularHoras(LocalDateTime.of(2026,4,17,8,0), LocalDateTime.of(2026,4,17,9,10)));
    }
    @Test void exactoUnaHora() {
        assertEquals(1L, svc.calcularHoras(LocalDateTime.of(2026,4,17,8,0), LocalDateTime.of(2026,4,17,9,0)));
    }
    @Test void minimoUnaHora() {
        assertEquals(1L, svc.calcularHoras(LocalDateTime.of(2026,4,17,8,0), LocalDateTime.of(2026,4,17,8,1)));
    }
}
