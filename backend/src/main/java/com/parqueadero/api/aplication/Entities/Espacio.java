package com.parqueadero.api.aplication.Entities;

import jakarta.persistence.*;
import lombok.*;

// Lombok genera automáticamente los métodos getter, setter, constructor con todos los campos,
// constructor vacío y el patrón Builder (para crear objetos con sintaxis fluida).
// @Entity le dice a JPA que esta clase representa una tabla en la base de datos.
// @Table(name="espacios") define el nombre exacto de la tabla en la BD.
@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
@Entity @Table(name = "espacios")
public class Espacio {

    // @Id indica que este campo es la clave primaria de la tabla.
    // @GeneratedValue con IDENTITY hace que la base de datos genere el ID automáticamente (autoincremento).
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // El código identifica visualmente el espacio (ej: "A1", "B3").
    // nullable=false → no puede estar vacío.
    // unique=true → no pueden existir dos espacios con el mismo código.
    // length=10 → máximo 10 caracteres.
    @Column(nullable = false, unique = true, length = 10)
    private String codigo;
}
