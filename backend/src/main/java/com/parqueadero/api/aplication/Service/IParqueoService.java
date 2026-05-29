package com.parqueadero.api.aplication.Service;
import com.parqueadero.api.aplication.Entities.Parqueo;
import com.parqueadero.api.aplication.DTO.EspacioDTO;
import java.util.List;
public interface IParqueoService {
    Parqueo registrarEntrada(Long vid, Long eid, String by);
    Parqueo registrarSalida(Long id);
    List<Parqueo> listar();
    List<Parqueo> listarActivos();
    List<EspacioDTO> listarEspacios();
}
