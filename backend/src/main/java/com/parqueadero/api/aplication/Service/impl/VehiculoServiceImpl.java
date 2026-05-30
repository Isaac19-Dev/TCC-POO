package com.parqueadero.api.aplication.Service.impl;

import com.parqueadero.api.aplication.Service.IVehiculoService;
import com.parqueadero.api.aplication.Entities.Vehiculo;
import com.parqueadero.api.aplication.Repository.VehiculoRepository;
import java.util.List;
import org.springframework.stereotype.Service;

// Implementación concreta del servicio de vehículos.
// @Service → Spring la registra como Bean y la inyecta donde se pida IVehiculoService.
@Service
public class VehiculoServiceImpl implements IVehiculoService {

    // Repositorio para acceder a la base de datos. Se inyecta por constructor (buena práctica).
    private final VehiculoRepository repo;

    // Inyección de dependencia por constructor: Spring pasa automáticamente el repositorio.
    public VehiculoServiceImpl(VehiculoRepository r) {
        repo = r;
    }

    // Crea un nuevo vehículo con validaciones de negocio.
    @Override
    public Vehiculo crear(Vehiculo v) {
        // Valida que los campos obligatorios no sean nulos.
        if (v == null || v.getPlaca() == null || v.getTipo() == null || v.getPropietario() == null)
            throw new IllegalArgumentException("Datos invalidos");

        // Fuerza el ID a null para que la BD genere uno nuevo (evita que el cliente envíe un ID).
        v.setId(null);

        // Normaliza la placa: elimina espacios y convierte a mayúsculas (ej: " abc123 " → "ABC123").
        v.setPlaca(v.getPlaca().trim().toUpperCase());

        // Elimina espacios al inicio y al final del nombre del propietario.
        v.setPropietario(v.getPropietario().trim());

        // Verifica que no exista otro vehículo con la misma placa (sin importar mayúsculas).
        if (repo.findByPlacaIgnoreCase(v.getPlaca()).isPresent())
            throw new IllegalArgumentException("Placa registrada");

        // Guarda y retorna el vehículo persistido en la base de datos.
        return repo.save(v);
    }

    // Retorna todos los vehículos registrados en el sistema.
    @Override
    public List<Vehiculo> listar() {
        return repo.findAll();
    }

    // Busca un vehículo por ID. Si no existe, lanza una excepción controlada.
    @Override
    public Vehiculo obtener(Long id) {
        return repo.findById(id).orElseThrow(() -> new IllegalArgumentException("No encontrado"));
    }

    // Actualiza los datos de un vehículo existente con validaciones de negocio.
    @Override
    public Vehiculo actualizar(Long id, Vehiculo v) {
        // Valida que los datos del request no sean nulos.
        if (v == null || v.getPlaca() == null || v.getTipo() == null || v.getPropietario() == null)
            throw new IllegalArgumentException("Datos invalidos");

        // Obtiene el vehículo actual de la BD (lanza excepción si no existe).
        Vehiculo a = obtener(id);

        // Normaliza la nueva placa a mayúsculas.
        String p = v.getPlaca().trim().toUpperCase();

        // Verifica que la nueva placa no esté siendo usada por OTRO vehículo diferente.
        // El filter excluye el vehículo actual para que pueda actualizar sin conflicto si 
        // la placa es la misma.
        if (repo.findByPlacaIgnoreCase(p).filter(e -> !e.getId().equals(id)).isPresent())
            throw new IllegalArgumentException("Placa registrada");

        // Actualiza los campos del vehículo existente.
        a.setPlaca(p);
        a.setTipo(v.getTipo());
        a.setPropietario(v.getPropietario().trim());

        // Guarda los cambios y retorna el vehículo actualizado.
        return repo.save(a);
    }

    // Elimina un vehículo por ID. Primero lo busca para asegurarse de que existe.
    @Override
    public void eliminar(Long id) {
        repo.delete(obtener(id));
    }
}
