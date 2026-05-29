package com.parqueadero.api.aplication.Service.impl; // Paquete impl
import com.parqueadero.api.aplication.Service.IVehiculoService; // Importa interfaz
import com.parqueadero.api.aplication.Entities.Vehiculo; // Importa entidad
import com.parqueadero.api.aplication.Repository.VehiculoRepository; // Importa repositorio
import java.util.List; // Utilidades java
import org.springframework.stereotype.Service; // Spring anotaciones

@Service public class VehiculoServiceImpl implements IVehiculoService { // Implementación lógica de negocio para gestionar el catálogo de vehículos (clientes)
    private final VehiculoRepository repo; // Referencia al repositorio de la BD
    public VehiculoServiceImpl(VehiculoRepository r) { repo = r; } // Constructor con inyección de dependencias
    @Override public Vehiculo crear(Vehiculo v) { // Método para registrar un nuevo carro o moto
        if(v==null||v.getPlaca()==null||v.getTipo()==null||v.getPropietario()==null) throw new IllegalArgumentException("Datos invalidos"); // Validación defensiva básica asegurando que vengan todos los campos requeridos
        v.setId(null); v.setPlaca(v.getPlaca().trim().toUpperCase()); v.setPropietario(v.getPropietario().trim()); // Normaliza la entrada de texto quitando espacios y forzando mayúsculas a las placas
        if(repo.findByPlacaIgnoreCase(v.getPlaca()).isPresent()) throw new IllegalArgumentException("Placa registrada"); // Valida en BD que no exista ya otra placa idéntica
        return repo.save(v); // Finalmente persiste (guarda) el objeto en la tabla MariaDB
    } // Cierre crear
    @Override public List<Vehiculo> listar() { return repo.findAll(); } // Devuelve absolutamente todos los vehículos de la tabla usando findAll
    @Override public Vehiculo obtener(Long id) { return repo.findById(id).orElseThrow(() -> new IllegalArgumentException("No encontrado")); } // Retorna un vehículo buscando por ID o lanza excepción 400
    @Override public Vehiculo actualizar(Long id, Vehiculo v) { // Método para editar datos del cliente
        if(v==null||v.getPlaca()==null||v.getTipo()==null||v.getPropietario()==null) throw new IllegalArgumentException("Datos invalidos"); // Misma validación defensiva
        Vehiculo a = obtener(id); String p = v.getPlaca().trim().toUpperCase(); // Obtiene la instancia que está en base de datos y prepara la nueva placa
        if(repo.findByPlacaIgnoreCase(p).filter(e -> !e.getId().equals(id)).isPresent()) throw new IllegalArgumentException("Placa registrada"); // Valida colisión de placas (se asegura que si otra persona ya tiene esa placa, no sea este mismo usuario modificado)
        a.setPlaca(p); a.setTipo(v.getTipo()); a.setPropietario(v.getPropietario().trim()); // Reemplaza los atributos en memoria
        return repo.save(a); // Ejecuta el "UPDATE" en la base de datos
    } // Cierre actualizar
    @Override public void eliminar(Long id) { repo.delete(obtener(id)); } // Busca que el ID exista, si existe, ejecuta un DELETE en la BD
} // Cierre de la clase
