package com.parqueadero.api.aplication.Entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

/**
 * Entidad que representa la tabla 'parqueos' en base de datos.
 * Registra cada vez que un vehículo entra o sale, como una bitácora o recibo de pago.
 */
@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor 
@Entity @Table(name="parqueos")
public class Parqueo {
    
    // Identificador único (Primary Key)
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) 
    private Long id; 
    
    // Relación al vehículo que ocupa la plaza (Llave foránea obligatoria)
    @ManyToOne(optional=false) @JoinColumn(name="vehiculo_id", nullable=false) 
    private Vehiculo vehiculo; 
    
    // Relación a la plaza física (A1, A2, etc.)
    @ManyToOne @JoinColumn(name="espacio_id") 
    private Espacio espacio; 
    
    // Fecha y hora del momento exacto del ingreso
    @Column(nullable=false) 
    private LocalDateTime fechaEntrada; 
    
    // Fecha y hora del momento exacto del retiro
    private LocalDateTime fechaSalida; 
    
    // Cantidad de horas a cobrar (calculado matemáticamente)
    @Column(nullable=false) 
    private long horas; 
    
    // Total de dinero a pagar
    @Column(nullable=false) 
    private long total; 
    
    // Estado del ticket: ACTIVO (sigue dentro) o FINALIZADO (ya pagó y salió)
    @Enumerated(EnumType.STRING) @Column(nullable=false) 
    private EstadoParqueo estado; 
    
    // Quién (usuario/admin) operó el registro
    @Column(nullable=false) 
    private String registradoPor; 
}
