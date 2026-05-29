package com.parqueadero.api.aplication.Service.impl; // Paquete de implementación
import com.parqueadero.api.aplication.Entities.*; // Importa todas las entidades (Parqueo, Vehiculo, etc.)
import com.parqueadero.api.aplication.Repository.*; // Importa los repositorios (ParqueoRepository, etc.) para consultar la BD
import com.parqueadero.api.aplication.DTO.EspacioDTO; // Importa el DTO
import com.parqueadero.api.aplication.Service.IParqueoService; // Importa la interfaz IParqueoService
import java.time.*; // Importa el paquete de fechas de Java (LocalDateTime)
import java.util.*; // Importa colecciones (List, Set, Map)
import java.util.stream.Collectors; // Importa utilidades para trabajar con flujos de datos (Streams)
import org.springframework.stereotype.Service; // Anotación para que Spring la reconozca

@Service public class ParqueoServiceImpl implements IParqueoService { // Clase donde está toda la lógica matemática y de validación de los parqueos
    private static final long TC = 3000L, TM = 1500L; // Define constantes de tarifas (Tarifa Carro = 3000, Tarifa Moto = 1500)
    private final ParqueoRepository pr; private final EspacioRepository er; private final VehiculoRepository vr; // Declaración de dependencias inmutables a los repositorios
    public ParqueoServiceImpl(ParqueoRepository pr, EspacioRepository er, VehiculoRepository vr) { this.pr=pr; this.er=er; this.vr=vr; } // Constructor para que Spring inyecte los repositorios

    @Override public Parqueo registrarEntrada(Long vid, Long eid, String by) { // Lógica para que un vehículo entre
        if (vid == null) throw new IllegalArgumentException("Vehiculo obligatorio"); // Lanza error si no envían el id del vehículo
        Vehiculo v = vr.findById(vid).orElseThrow(() -> new IllegalArgumentException("No encontrado")); // Busca el vehículo en la base de datos, falla si no existe
        if (pr.findByVehiculoIdAndEstado(vid, EstadoParqueo.ACTIVO).isPresent()) throw new IllegalArgumentException("Ya tiene parqueo activo"); // Si el vehículo ya está parqueado, no lo deja parquear de nuevo
        Set<Long> ocp = pr.findByEstado(EstadoParqueo.ACTIVO).stream().filter(p -> p.getEspacio() != null).map(p -> p.getEspacio().getId()).collect(Collectors.toSet()); // Extrae un conjunto (Set) con todos los IDs de los espacios que están actualmente ocupados
        Espacio esp = eid != null ? er.findById(eid).orElseThrow(() -> new IllegalArgumentException("No existe plaza")) : er.findAll().stream().filter(e -> !ocp.contains(e.getId())).findFirst().orElseThrow(() -> new IllegalArgumentException("Sin espacios")); // Si le pasan un ID de espacio lo busca, sino busca el primer espacio libre que encuentre. Falla si está lleno.
        if (eid != null && ocp.contains(esp.getId())) throw new IllegalArgumentException("Plaza ocupada"); // Si le piden un espacio específico y resulta estar ocupado, lanza excepción
        return pr.save(Parqueo.builder().vehiculo(v).espacio(esp).registradoPor(by).fechaEntrada(LocalDateTime.now()).estado(EstadoParqueo.ACTIVO).build()); // Construye un nuevo objeto Parqueo activo con la fecha/hora actual y lo guarda en la BD
    } // Cierre método
    @Override public Parqueo registrarSalida(Long id) { // Lógica para terminar un parqueo y cobrar
        Parqueo p = pr.findById(id).orElseThrow(() -> new IllegalArgumentException("No encontrado")); // Busca el registro de parqueo por su ID
        if (p.getEstado() == EstadoParqueo.FINALIZADO) throw new IllegalArgumentException("Ya finalizado"); // Verifica que el parqueo no haya sido cobrado antes
        LocalDateTime sal = LocalDateTime.now(); long h = (long) Math.ceil(Math.max(0, Duration.between(p.getFechaEntrada(), sal).toMinutes()) / 60.0); // Calcula la diferencia en minutos, divide por 60 para horas, y redondea hacia arriba usando Math.ceil (ej. 1h 10m = 2h)
        p.setFechaSalida(sal); p.setHoras(h); p.setTotal(h * (p.getVehiculo().getTipo() == TipoVehiculo.CARRO ? TC : TM)); p.setEstado(EstadoParqueo.FINALIZADO); // Actualiza la entidad con la fecha de salida, total de horas, precio multiplicado por tarifa según el tipo, y estado finalizado
        return pr.save(p); // Actualiza el registro guardándolo en base de datos
    } // Cierre método
    @Override public List<Parqueo> listar() { return pr.findAll(); } // Retorna el histórico completo leyendo directo del repositorio
    @Override public List<Parqueo> listarActivos() { return pr.findByEstado(EstadoParqueo.ACTIVO); } // Filtra los parqueos por el enum ACTIVO
    @Override public List<EspacioDTO> listarEspacios() { // Lógica compleja para fusionar espacios físicos con vehículos parqueados y pintarlos en el frontend
        Map<Long, Parqueo> act = pr.findByEstado(EstadoParqueo.ACTIVO).stream().filter(p -> p.getEspacio() != null).collect(Collectors.toMap(p -> p.getEspacio().getId(), p -> p)); // Busca los parqueos activos y crea un mapa cruzando el ID del espacio con el objeto Parqueo correspondiente
        return er.findAll().stream().map(e -> act.containsKey(e.getId()) ? new EspacioDTO(e.getId(), e.getCodigo(), "OCUPADO", act.get(e.getId()).getId(), act.get(e.getId()).getVehiculo().getId(), act.get(e.getId()).getVehiculo().getPlaca(), act.get(e.getId()).getVehiculo().getTipo().name(), act.get(e.getId()).getVehiculo().getPropietario(), act.get(e.getId()).getRegistradoPor(), act.get(e.getId()).getFechaEntrada()) : new EspacioDTO(e.getId(), e.getCodigo(), "LIBRE", null, null, null, null, null, null, null)).toList(); // Itera los 5 espacios. Si está en el mapa, devuelve un DTO lleno con los datos del ocupante ("OCUPADO"), de lo contrario devuelve el espacio como "LIBRE" sin datos asociados.
    } // Cierre método
    public long calcularHoras(LocalDateTime e, LocalDateTime s) { return (long) Math.ceil(Math.max(0, Duration.between(e, s).toMinutes()) / 60.0); } // Función auxiliar expuesta para poder ser probada en los Unit Tests
}
