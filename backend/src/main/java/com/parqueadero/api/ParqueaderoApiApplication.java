package com.parqueadero.api;

// Importaciones necesarias para crear los espacios iniciales
import com.parqueadero.api.aplication.Entities.Espacio;
import com.parqueadero.api.aplication.Repository.EspacioRepository;
import java.util.List;

// Importaciones de Spring Boot para arrancar la aplicación
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.boot.CommandLineRunner;

// @SpringBootApplication activa la autoconfiguración de Spring Boot, el escaneo de componentes
// y la configuración de la aplicación en una sola anotación.
@SpringBootApplication
// Le indica a Spring dónde buscar los repositorios JPA (acceso a base de datos)
@org.springframework.data.jpa.repository.config.EnableJpaRepositories("com.parqueadero.api.aplication.Repository")
// Le indica a Spring dónde buscar las entidades (tablas de la base de datos)
@org.springframework.boot.autoconfigure.domain.EntityScan("com.parqueadero.api.aplication.Entities")
public class ParqueaderoApiApplication {

    // Punto de entrada principal de la aplicación Java
    public static void main(String[] args) {
        SpringApplication.run(ParqueaderoApiApplication.class, args);
    }

    // @Bean registra este método como un componente gestionado por Spring.
    // CommandLineRunner se ejecuta automáticamente UNA SOLA VEZ justo después de que
    // la aplicación arranca y la base de datos ya está lista.
    @Bean
    CommandLineRunner init(EspacioRepository er) {
        return args -> {
            // Solo crea los espacios si la tabla está vacía (evita duplicados al reiniciar)
            if (er.count() == 0) {
                // Guarda los 5 espacios de parqueo iniciales en la base de datos
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
