package com.parqueadero.api.aplication.Entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

// Entidad principal del sistema. Representa una sesión de parqueo (desde entrada hasta salida).
// @Entity → tabla "parqueos" en la base de datos.
@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
@Entity @Table(name = "parqueos")
public class Parqueo {

    // Clave primaria autoincremental.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relación ManyToOne: muchos parqueos pueden pertenecer a un mismo vehículo.
    // optional=false → el vehículo es obligatorio (no puede ser null).
    // @JoinColumn define la columna de llave foránea en la tabla "parqueos".
    @ManyToOne(optional = false)
    @JoinColumn(name = "vehiculo_id", nullable = false)
    private Vehiculo vehiculo;

    // Relación ManyToOne con el espacio donde está ubicado el vehículo.
    // No es obligatorio (puede ser null si el espacio se libera antes de que el registro finalice).
    @ManyToOne
    @JoinColumn(name = "espacio_id")
    private Espacio espacio;

    // Fecha y hora exacta en que el vehículo ingresó al parqueadero.
    @Column(nullable = false)
    private LocalDateTime fechaEntrada;

    // Fecha y hora exacta en que el vehículo salió. Es null mientras el parqueo está activo.
    private LocalDateTime fechaSalida;

    // Número de horas cobradas (se calcula al registrar la salida, redondeando hacia arriba).
    @Column(nullable = false)
    private long horas;

    // Monto total a cobrar en pesos. Calculado como: horas × tarifa según tipo de vehículo.
    @Column(nullable = false)
    private long total;

    // Estado del parqueo: ACTIVO (vehículo adentro) o FINALIZADO (vehículo salió y se cobró).
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoParqueo estado;

    // Nombre del usuario (operario/admin) que registró la entrada del vehículo.
    @Column(nullable = false)
    private String registradoPor;
}
