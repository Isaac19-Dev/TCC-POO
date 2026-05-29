package com.parqueadero.api; // Define el paquete base de la aplicación principal
import com.parqueadero.api.aplication.Entities.Espacio; // Importa la entidad Espacio para inicializar datos
import com.parqueadero.api.aplication.Repository.EspacioRepository; // Importa el repositorio para guardar en base de datos
import java.util.List; // Importa List de Java para crear la lista de espacios
import org.springframework.boot.SpringApplication; // Clase principal de Spring Boot para arrancar la app
import org.springframework.boot.autoconfigure.SpringBootApplication; // Anotación que auto-configura el framework
import org.springframework.context.annotation.Bean; // Indica que un método produce un bean gestionado por Spring
import org.springframework.boot.CommandLineRunner; // Interfaz para ejecutar código justo después de arrancar

@SpringBootApplication // Configura la aplicación Spring Boot de forma automática
@org.springframework.data.jpa.repository.config.EnableJpaRepositories("com.parqueadero.api.aplication.Repository") // Le dice a Spring dónde buscar los repositorios JPA
@org.springframework.boot.autoconfigure.domain.EntityScan("com.parqueadero.api.aplication.Entities") // Le dice a Spring dónde buscar las entidades de base de datos
public class ParqueaderoApiApplication { // Clase principal que inicia el backend
    public static void main(String[] args) { SpringApplication.run(ParqueaderoApiApplication.class, args); } // Método main que arranca el servidor embebido (Tomcat)
    @Bean CommandLineRunner init(EspacioRepository er) { // Bean que se ejecuta al iniciar la aplicación, inyecta el repositorio
        return args -> { // Expresión lambda que recibe los argumentos de consola
            if (er.count() == 0) er.saveAll(List.of(Espacio.builder().codigo("A1").build(), Espacio.builder().codigo("A2").build(), Espacio.builder().codigo("A3").build(), Espacio.builder().codigo("A4").build(), Espacio.builder().codigo("A5").build())); // Si no hay espacios, crea e inserta los 5 espacios fijos (A1 a A5) en la BD
        };
    }
}
