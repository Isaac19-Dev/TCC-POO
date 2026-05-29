package com.parqueadero.api.aplication.Service;
import com.parqueadero.api.aplication.Entities.Vehiculo;
import java.util.List;
public interface IVehiculoService {
    Vehiculo crear(Vehiculo v);
    List<Vehiculo> listar();
    Vehiculo obtener(Long id);
    Vehiculo actualizar(Long id, Vehiculo v);
    void eliminar(Long id);
}
