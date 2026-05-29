package com.parqueadero.api.aplication.Entities; // Define el paquete Entities
import jakarta.persistence.*; // Anotaciones de BD
import lombok.*; // Lombok getters/setters

@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor @Entity @Table(name="espacios") // Crea tabla 'espacios' y métodos lombok
public class Espacio { // Entidad que representa físicamente un cajón de parqueo en el local (ej. A1, A2)
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id; // Clave primaria del espacio, se autoasigna 1, 2, 3...
    @Column(nullable=false, unique=true, length=10) private String codigo; // Columna con el código o nombre del espacio (Ej: "A1", "A2"). Es único.
}
