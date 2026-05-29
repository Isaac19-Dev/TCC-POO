package com.parqueadero.api.aplication.Service.impl;

import com.parqueadero.api.aplication.Entities.*;
import com.parqueadero.api.aplication.Repository.*;
import com.parqueadero.api.aplication.DTO.EspacioDTO;
import com.parqueadero.api.aplication.Service.IParqueoService;
import java.time.*;
import java.util.*;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

/**
 * Servicio que contiene toda la lógica matemática y restricciones del parqueadero.
 */
@Service 
public class ParqueoServiceImpl implements IParqueoService {
    
    // Tarifas fijas
    private static final long TC = 3000L; // Tarifa Carro
    private static final long TM = 1500L; // Tarifa Moto
    
    // Inyección de Repositorios para lectura y escritura en la Base de Datos
    private final ParqueoRepository pr; 
    private final EspacioRepository er; 
    private final VehiculoRepository vr; 

    /**
     * Constructor con inyección.
     */
    public ParqueoServiceImpl(ParqueoRepository pr, EspacioRepository er, VehiculoRepository vr) { 
        this.pr = pr; 
        this.er = er; 
        this.vr = vr; 
    }

    /**
     * Realiza el ingreso de un vehículo, buscando un espacio y marcándolo como activo.
     */
    @Override 
    public Parqueo registrarEntrada(Long vid, Long eid, String by) { 
        if (vid == null) throw new IllegalArgumentException("Vehiculo obligatorio"); 
        
        // Busca al vehículo. Lanza excepción si no está registrado
        Vehiculo v = vr.findById(vid).orElseThrow(() -> new IllegalArgumentException("No encontrado")); 
        
        // Verifica que el mismo vehículo no esté ya adentro
        if (pr.findByVehiculoIdAndEstado(vid, EstadoParqueo.ACTIVO).isPresent()) 
            throw new IllegalArgumentException("Ya tiene parqueo activo"); 
            
        // Obtiene el conjunto de IDs de espacios que actualmente están ocupados
        Set<Long> ocp = pr.findByEstado(EstadoParqueo.ACTIVO).stream()
            .filter(p -> p.getEspacio() != null)
            .map(p -> p.getEspacio().getId())
            .collect(Collectors.toSet()); 
            
        // Resuelve qué espacio usar: el que se pidió o el primer libre disponible
        Espacio esp = eid != null 
            ? er.findById(eid).orElseThrow(() -> new IllegalArgumentException("No existe plaza")) 
            : er.findAll().stream().filter(e -> !ocp.contains(e.getId())).findFirst().orElseThrow(() -> new IllegalArgumentException("Sin espacios")); 
            
        // Valida que el espacio solicitado no esté ocupado
        if (eid != null && ocp.contains(esp.getId())) throw new IllegalArgumentException("Plaza ocupada"); 
        
        // Crea el registro del parqueo, guarda en base de datos y retorna
        return pr.save(Parqueo.builder().vehiculo(v).espacio(esp).registradoPor(by).fechaEntrada(LocalDateTime.now()).estado(EstadoParqueo.ACTIVO).build()); 
    }
    
    /**
     * Marca un parqueo como finalizado, calcula sus horas y realiza el cobro.
     */
    @Override 
    public Parqueo registrarSalida(Long id) { 
        // Busca el registro de ingreso original
        Parqueo p = pr.findById(id).orElseThrow(() -> new IllegalArgumentException("No encontrado")); 
        
        // Valida que no se intente cobrar dos veces
        if (p.getEstado() == EstadoParqueo.FINALIZADO) throw new IllegalArgumentException("Ya finalizado"); 
        
        // Establece la fecha de salida y calcula horas (redondeando hacia arriba ej. 1h10m = 2h)
        LocalDateTime sal = LocalDateTime.now(); 
        long h = (long) Math.ceil(Math.max(0, Duration.between(p.getFechaEntrada(), sal).toMinutes()) / 60.0); 
        
        // Actualiza el registro con totales, el costo (basado en si es carro o moto) y cambia estado a FINALIZADO
        p.setFechaSalida(sal); 
        p.setHoras(h); 
        p.setTotal(h * (p.getVehiculo().getTipo() == TipoVehiculo.CARRO ? TC : TM)); 
        p.setEstado(EstadoParqueo.FINALIZADO); 
        
        return pr.save(p); 
    }
    
    /**
     * Retorna todo el histórico del parqueadero.
     */
    @Override 
    public List<Parqueo> listar() { 
        return pr.findAll(); 
    }
    
    /**
     * Retorna únicamente los carros que siguen ocupando un espacio.
     */
    @Override 
    public List<Parqueo> listarActivos() { 
        return pr.findByEstado(EstadoParqueo.ACTIVO); 
    }
    
    /**
     * Retorna el estado físico actual de los espacios para mostrar en pantalla (Libre u Ocupado).
     */
    @Override 
    public List<EspacioDTO> listarEspacios() { 
        // Obtiene qué parqueo activo tiene qué espacio asignado
        Map<Long, Parqueo> act = pr.findByEstado(EstadoParqueo.ACTIVO).stream()
            .filter(p -> p.getEspacio() != null)
            .collect(Collectors.toMap(p -> p.getEspacio().getId(), p -> p)); 
            
        // Itera los 5 espacios. Si el espacio está en el mapa, arma un DTO lleno; de lo contrario, un DTO vacío "LIBRE"
        return er.findAll().stream().map(e -> act.containsKey(e.getId()) 
            ? new EspacioDTO(e.getId(), e.getCodigo(), "OCUPADO", act.get(e.getId()).getId(), act.get(e.getId()).getVehiculo().getId(), act.get(e.getId()).getVehiculo().getPlaca(), act.get(e.getId()).getVehiculo().getTipo().name(), act.get(e.getId()).getVehiculo().getPropietario(), act.get(e.getId()).getRegistradoPor(), act.get(e.getId()).getFechaEntrada()) 
            : new EspacioDTO(e.getId(), e.getCodigo(), "LIBRE", null, null, null, null, null, null, null)
        ).toList(); 
    }
}
