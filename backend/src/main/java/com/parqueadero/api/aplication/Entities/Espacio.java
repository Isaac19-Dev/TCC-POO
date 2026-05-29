package com.parqueadero.api.aplication.Entities;

import jakarta.persistence.*;
import lombok.*;

/**
 * Entidad que representa la tabla 'espacios' en la base de datos.
 * Define la cantidad física de puestos de aparcamiento en el local.
 */
@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor 
@Entity @Table(name="espacios")
public class Espacio {
    
    // Identificador primario
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) 
    private Long id; 
    
    // Nombre visible del espacio (ej. "A1", "A2")
    @Column(nullable=false, unique=true, length=10) 
    private String codigo; 
}
