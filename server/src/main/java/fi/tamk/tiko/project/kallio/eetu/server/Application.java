package fi.tamk.tiko.project.kallio.eetu.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The actual Spring Boot application
 *
 * @author Henri Kankaanpää
 * @author Eetu Kallio
 * @version 1.0
 * @since 1.0
 */
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
