package com.parqueadero.api;

import com.parqueadero.api.aplication.Entities.Espacio;
import com.parqueadero.api.aplication.Repository.EspacioRepository;
import java.util.List;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.boot.CommandLineRunner;

/**
 * Clase principal que arranca la aplicación Spring Boot.
 * Configura el escaneo de entidades y repositorios de JPA.
 */
@SpringBootApplication
@org.springframework.data.jpa.repository.config.EnableJpaRepositories("com.parqueadero.api.aplication.Repository")
@org.springframework.boot.autoconfigure.domain.EntityScan("com.parqueadero.api.aplication.Entities")
public class ParqueaderoApiApplication {

    /**
     * Método principal que inicia el servidor embebido (Tomcat).
     */
    public static void main(String[] args) { 
        SpringApplication.run(ParqueaderoApiApplication.class, args); 
    }

    /**
     * Se ejecuta automáticamente al arrancar la aplicación.
     * Crea los 5 espacios fijos del parqueadero si la base de datos está vacía.
     */
    @Bean 
    CommandLineRunner init(EspacioRepository er) {
        return args -> {
            // Verifica si no hay registros en la tabla espacios
            if (er.count() == 0) {
                // Guarda los 5 espacios (A1 al A5) por defecto
                er.saveAll(List.of(
                    Espacio.builder().codigo("A1").build(), 
                    Espacio.builder().codigo("A2").build(), 
                    Espacio.builder().codigo("A3").build(), 
                    Espacio.builder().codigo("A4").build(), 
                    Espacio.builder().codigo("A5").build()
                ));
            }
        };
    }
}
