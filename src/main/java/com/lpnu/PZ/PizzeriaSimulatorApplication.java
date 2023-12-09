package com.lpnu.PZ;

import com.lpnu.PZ.dto.PizzeriaConfigurationDTO;
import com.lpnu.PZ.services.Pizzeria;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class PizzeriaSimulatorApplication {

    public static void main(String[] args) {
        SpringApplication.run(PizzeriaSimulatorApplication.class, args);

        final var pizzeriaConfigurationDTO = new PizzeriaConfigurationDTO();
        pizzeriaConfigurationDTO.setCooksNumber(8);
        pizzeriaConfigurationDTO.setPizzasNumber(8);
        pizzeriaConfigurationDTO.setMinimalPizzaCreationTime(13);
        pizzeriaConfigurationDTO.setIntervalGenerationStrategy(true);
        Pizzeria pizzeria = new Pizzeria();
        pizzeria.configurePizzeria(pizzeriaConfigurationDTO);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            log.info("Shutting down gracefully...");
            pizzeria.shutdown();
            log.info("Graceful shutdown completed.");
        }));
    }

}
