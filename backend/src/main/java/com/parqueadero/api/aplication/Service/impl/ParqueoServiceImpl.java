package com.parqueadero.api.aplication.Service.impl;

import com.parqueadero.api.aplication.Entities.*;
import com.parqueadero.api.aplication.Repository.*;
import com.parqueadero.api.aplication.DTO.EspacioDTO;
import com.parqueadero.api.aplication.Service.IParqueoService;
import java.time.*;
import java.util.*;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

// Implementación del servicio de parqueos. Contiene toda la lógica de negocio del estacionamiento.
// @Service → Spring la detecta y la registra como Bean.
@Service
public class ParqueoServiceImpl implements IParqueoService {

    // ────────── TARIFAS ──────────
    // TC = Tarifa Carro: $3.000 por hora (en pesos colombianos)
    // TM = Tarifa Moto:  $1.500 por hora (en pesos colombianos)
    private static final long TC = 3000L, TM = 1500L;

    // Repositorios inyectados para acceder a las tres tablas principales.
    private final ParqueoRepository pr;
    private final EspacioRepository er;
    private final VehiculoRepository vr;

    // Inyección de dependencias por constructor (buena práctica en Spring).
    public ParqueoServiceImpl(ParqueoRepository pr, EspacioRepository er, VehiculoRepository vr) {
        this.pr = pr;
        this.er = er;
        this.vr = vr;
    }

    // ────────── REGISTRAR ENTRADA ──────────
    // Registra el ingreso de un vehículo al parqueadero.
    // vid → ID del vehículo | eid → ID del espacio (null = asignar automático) | by → usuario que registra
    @Override
    public Parqueo registrarEntrada(Long vid, Long eid, String by) {
        // El vehículo es obligatorio: lanza error si no se envía.
        if (vid == null) throw new IllegalArgumentException("Vehiculo obligatorio");

        // Busca el vehículo en la base de datos. Lanza error si no existe.
        Vehiculo v = vr.findById(vid).orElseThrow(() -> new IllegalArgumentException("No encontrado"));

        // Verifica que el vehículo no tenga ya un parqueo activo.
        // Un vehículo no puede entrar dos veces sin haber salido.
        if (pr.findByVehiculoIdAndEstado(vid, EstadoParqueo.ACTIVO).isPresent())
            throw new IllegalArgumentException("Ya tiene parqueo activo");

        // Obtiene los IDs de todos los espacios que están ocupados actualmente.
        // Filtra los parqueos ACTIVOS que tienen un espacio asignado y extrae sus IDs.
        Set<Long> ocp = pr.findByEstado(EstadoParqueo.ACTIVO)
            .stream()
            .filter(p -> p.getEspacio() != null)
            .map(p -> p.getEspacio().getId())
            .collect(Collectors.toSet());

        // Determina el espacio a asignar:
        // Si se especificó un espacio (eid != null) → usa ese espacio (si existe).
        // Si no se especificó (eid == null) → busca el primer espacio libre automáticamente.
        Espacio esp = eid != null
            ? er.findById(eid).orElseThrow(() -> new IllegalArgumentException("No existe plaza"))
            : er.findAll().stream()
                .filter(e -> !ocp.contains(e.getId()))  // filtra los ocupados
                .findFirst()                              // toma el primero libre
                .orElseThrow(() -> new IllegalArgumentException("Sin espacios"));

        // Si se especificó un espacio pero ya está ocupado → error.
        if (eid != null && ocp.contains(esp.getId()))
            throw new IllegalArgumentException("Plaza ocupada");

        // Crea y guarda el registro de parqueo con estado ACTIVO.
        // La fechaEntrada se toma en el momento exacto del registro (LocalDateTime.now()).
        // horas=0 y total=0 porque aún no ha salido.
        return pr.save(Parqueo.builder()
            .vehiculo(v)
            .espacio(esp)
            .registradoPor(by)
            .fechaEntrada(LocalDateTime.now())
            .estado(EstadoParqueo.ACTIVO)
            .build());
    }

    // ────────── REGISTRAR SALIDA ──────────
    // Finaliza un parqueo: calcula el tiempo, aplica la tarifa y cobra.
    // id → ID del registro de parqueo a finalizar.
    @Override
    public Parqueo registrarSalida(Long id) {
        // Busca el parqueo. Lanza error si no existe.
        Parqueo p = pr.findById(id).orElseThrow(() -> new IllegalArgumentException("No encontrado"));

        // Evita registrar la salida de un parqueo que ya fue finalizado.
        if (p.getEstado() == EstadoParqueo.FINALIZADO)
            throw new IllegalArgumentException("Ya finalizado");

        // Captura la hora exacta de salida.
        LocalDateTime sal = LocalDateTime.now();

        // ── CÁLCULO DE HORAS ──
        // 1. Calcula los minutos entre la entrada y la salida (Duration.between().toMinutes()).
        // 2. Math.max(0, ...) garantiza que nunca sea negativo.
        // 3. Divide entre 60.0 para obtener horas con decimales.
        // 4. Math.ceil(...) redondea SIEMPRE hacia arriba:
        //    → 0 minutos = 0h, 1 minuto = 1h, 59 minutos = 1h, 61 minutos = 2h
        //    (Se cobra la hora completa aunque se pase solo 1 minuto)
        long h = (long) Math.ceil(
            Math.max(0, Duration.between(p.getFechaEntrada(), sal).toMinutes()) / 60.0
        );

        // Actualiza los campos del parqueo con los datos de salida.
        p.setFechaSalida(sal);
        p.setHoras(h);
        // Total = horas × tarifa. La tarifa depende del tipo de vehículo (CARRO o MOTO).
        p.setTotal(h * (p.getVehiculo().getTipo() == TipoVehiculo.CARRO ? TC : TM));
        p.setEstado(EstadoParqueo.FINALIZADO);

        // Guarda y retorna el parqueo actualizado.
        return pr.save(p);
    }

    // Retorna el historial completo (todos los parqueos, activos y finalizados).
    @Override
    public List<Parqueo> listar() {
        return pr.findAll();
    }

    // Retorna solo los parqueos actualmente ACTIVOS (vehículos dentro del parqueadero).
    @Override
    public List<Parqueo> listarActivos() {
        return pr.findByEstado(EstadoParqueo.ACTIVO);
    }

    // ────────── LISTAR ESPACIOS ──────────
    // Devuelve el estado de todos los espacios: libres y ocupados con detalle.
    @Override
    public List<EspacioDTO> listarEspacios() {
        // Crea un mapa (espacioId → Parqueo) con los parqueos activos que tienen espacio asignado.
        // Esto permite consultar rápidamente si un espacio está ocupado en O(1).
        Map<Long, Parqueo> act = pr.findByEstado(EstadoParqueo.ACTIVO)
            .stream()
            .filter(p -> p.getEspacio() != null)
            .collect(Collectors.toMap(p -> p.getEspacio().getId(), p -> p));

        // Para cada espacio en la base de datos, construye un EspacioDTO con su estado:
        return er.findAll().stream().map(e ->
            act.containsKey(e.getId())
                // Si el espacio está en el mapa → OCUPADO: incluye todos los datos del vehículo.
                ? new EspacioDTO(
                    e.getId(), e.getCodigo(), "OCUPADO",
                    act.get(e.getId()).getId(),
                    act.get(e.getId()).getVehiculo().getId(),
                    act.get(e.getId()).getVehiculo().getPlaca(),
                    act.get(e.getId()).getVehiculo().getTipo().name(),
                    act.get(e.getId()).getVehiculo().getPropietario(),
                    act.get(e.getId()).getRegistradoPor(),
                    act.get(e.getId()).getFechaEntrada())
                // Si el espacio NO está en el mapa → LIBRE: todos los campos de vehículo son null.
                : new EspacioDTO(e.getId(), e.getCodigo(), "LIBRE",
                    null, null, null, null, null, null, null)
        ).toList();
    }
}
