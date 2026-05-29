package com.parqueadero.api.aplication.Entities; // Define el paquete Entities de la base de datos
import jakarta.persistence.*; // Importa JPA (Java Persistence API) para mapear a base de datos
import lombok.*; // Importa Lombok (generación de getters/setters/constructores automáticos)
import java.time.LocalDateTime; // Importa manejo de tiempo

@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor @Entity @Table(name="parqueos") // Anotaciones Lombok y JPA para definir la tabla 'parqueos'
public class Parqueo { // Entidad principal del sistema que registra el historial
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id; // Define la clave primaria (ID) autoincremental de la tabla parqueos
    @ManyToOne(optional=false) @JoinColumn(name="vehiculo_id", nullable=false) private Vehiculo vehiculo; // Relación de cardinalidad Muchos-a-Uno con la tabla 'vehiculos' (llave foránea)
    @ManyToOne @JoinColumn(name="espacio_id") private Espacio espacio; // Relación de cardinalidad Muchos-a-Uno con la tabla 'espacios' (para saber dónde parqueó)
    @Column(nullable=false) private LocalDateTime fechaEntrada; // Columna 'fecha_entrada', obligatoria, para guardar la hora de ingreso
    private LocalDateTime fechaSalida; // Columna opcional 'fecha_salida' que se llena cuando el usuario se retira
    @Column(nullable=false) private long horas; // Columna para registrar cuántas horas duró parqueado
    @Column(nullable=false) private long total; // Columna para registrar el costo total a pagar (dinero)
    @Enumerated(EnumType.STRING) @Column(nullable=false) private EstadoParqueo estado; // Columna enumerada (ACTIVO o FINALIZADO) guardada como texto (STRING)
    @Column(nullable=false) private String registradoPor; // Columna para saber quién (usuario) operó el registro
}
