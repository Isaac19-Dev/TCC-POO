package com.parqueadero.api.aplication.Entities;

import jakarta.persistence.*;
import lombok.*;

// Lombok genera automáticamente getters, setters, constructores y el patrón Builder.
// @Entity → esta clase es una tabla de la base de datos.
// @Table(name="vehiculos") → nombre de la tabla en la BD.
@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
@Entity @Table(name = "vehiculos")
public class Vehiculo {

    // Clave primaria autoincremental generada por la base de datos.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Placa del vehículo. Debe ser única (no puede haber dos vehículos con la misma placa).
    // Máximo 10 caracteres (ej: "ABC-123").
    @Column(nullable = false, unique = true, length = 10)
    private String placa;

    // Tipo de vehículo: CARRO o MOTO (viene del enum TipoVehiculo).
    // EnumType.STRING guarda el nombre del enum como texto en la BD (ej: "CARRO"), 
    // no como número, lo que hace la BD más legible.
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoVehiculo tipo;

    // Nombre del dueño del vehículo. Máximo 100 caracteres.
    @Column(nullable = false, length = 100)
    private String propietario;
}
