package com.parqueadero.api.aplication.Service.impl;

import com.parqueadero.api.aplication.Service.IVehiculoService;
import com.parqueadero.api.aplication.Entities.Vehiculo;
import com.parqueadero.api.aplication.Repository.VehiculoRepository;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * Servicio encargado de gestionar el catálogo de clientes (vehículos).
 */
@Service 
public class VehiculoServiceImpl implements IVehiculoService {
    
    // Repositorio de vehículos (acceso a BD)
    private final VehiculoRepository repo; 

    /**
     * Constructor para la inyección del repositorio.
     */
    public VehiculoServiceImpl(VehiculoRepository r) { 
        repo = r; 
    }

    /**
     * Registra un nuevo vehículo asegurando que no tenga una placa duplicada.
     */
    @Override 
    public Vehiculo crear(Vehiculo v) { 
        // Valida que los datos básicos vengan completos
        if(v==null||v.getPlaca()==null||v.getTipo()==null||v.getPropietario()==null) 
            throw new IllegalArgumentException("Datos invalidos"); 
            
        // Limpia los datos (quita espacios, placa a mayúsculas) y desvincula cualquier ID falso
        v.setId(null); 
        v.setPlaca(v.getPlaca().trim().toUpperCase()); 
        v.setPropietario(v.getPropietario().trim()); 
        
        // Verifica si la placa ya fue registrada antes
        if(repo.findByPlacaIgnoreCase(v.getPlaca()).isPresent()) 
            throw new IllegalArgumentException("Placa registrada"); 
            
        // Guarda y retorna
        return repo.save(v); 
    }

    /**
     * Obtiene el listado de todos los clientes.
     */
    @Override 
    public List<Vehiculo> listar() { 
        return repo.findAll(); 
    }

    /**
     * Obtiene un cliente por su ID o arroja error 400 si no existe.
     */
    @Override 
    public Vehiculo obtener(Long id) { 
        return repo.findById(id).orElseThrow(() -> new IllegalArgumentException("No encontrado")); 
    }

    /**
     * Modifica los datos de un cliente existente, previniendo robarle la placa a otro cliente.
     */
    @Override 
    public Vehiculo actualizar(Long id, Vehiculo v) { 
        // Validación básica
        if(v==null||v.getPlaca()==null||v.getTipo()==null||v.getPropietario()==null) 
            throw new IllegalArgumentException("Datos invalidos"); 
            
        // Busca al cliente actual
        Vehiculo a = obtener(id); 
        String p = v.getPlaca().trim().toUpperCase(); 
        
        // Comprueba colisiones de placa excluyendo su propia placa
        if(repo.findByPlacaIgnoreCase(p).filter(e -> !e.getId().equals(id)).isPresent()) 
            throw new IllegalArgumentException("Placa registrada"); 
            
        // Actualiza los campos en memoria
        a.setPlaca(p); 
        a.setTipo(v.getTipo()); 
        a.setPropietario(v.getPropietario().trim()); 
        
        // Persiste en BD (UPDATE)
        return repo.save(a); 
    }

    /**
     * Borra un cliente por su ID.
     */
    @Override 
    public void eliminar(Long id) { 
        repo.delete(obtener(id)); 
    }
}
