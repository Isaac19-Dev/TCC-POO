package com.parqueadero.api.aplication.Entities; // Define el paquete Entities
import jakarta.persistence.*; // Anotaciones de base de datos JPA
import lombok.*; // Anotaciones de generación de código Lombok

@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor @Entity @Table(name="vehiculos") // Genera get, set, constructor vacío y full, y crea la tabla "vehiculos"
public class Vehiculo { // Entidad para almacenar la data de los clientes/autos
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id; // Clave primaria autoincremental de la tabla
    @Column(nullable=false, unique=true, length=10) private String placa; // Columna placa, es única, no nula, máximo 10 caracteres
    @Enumerated(EnumType.STRING) @Column(nullable=false, length=10) private TipoVehiculo tipo; // Tipo de vehículo (enum CARRO o MOTO) guardado como cadena de texto
    @Column(nullable=false, length=100) private String propietario; // Columna con el nombre completo del propietario (máx. 100 caracteres)
}
