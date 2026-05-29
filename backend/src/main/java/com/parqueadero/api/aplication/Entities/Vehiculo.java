package com.parqueadero.api.aplication.Entities;

import jakarta.persistence.*;
import lombok.*;

/**
 * Entidad que representa la tabla 'vehiculos' en la base de datos.
 * Contiene la información de los clientes regulares o eventuales.
 */
@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor 
@Entity @Table(name="vehiculos")
public class Vehiculo {
    
    // Clave primaria, número autoincremental
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) 
    private Long id; 
    
    // Identificador único en el mundo real (la matrícula), no se puede repetir
    @Column(nullable=false, unique=true, length=10) 
    private String placa; 
    
    // Tipo de vehículo (CARRO/MOTO) guardado como cadena de texto
    @Enumerated(EnumType.STRING) @Column(nullable=false, length=10) 
    private TipoVehiculo tipo; 
    
    // Nombre del propietario o responsable del vehículo
    @Column(nullable=false, length=100) 
    private String propietario; 
}
