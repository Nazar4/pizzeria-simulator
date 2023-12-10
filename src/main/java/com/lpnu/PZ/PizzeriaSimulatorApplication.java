package com.lpnu.PZ;

import com.lpnu.PZ.dto.PizzeriaConfigurationDTO;
import com.lpnu.PZ.services.Pizzeria;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@Slf4j
@SpringBootApplication
public class PizzeriaSimulatorApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(PizzeriaSimulatorApplication.class, args);

        Pizzeria pizzeria = context.getBean(Pizzeria.class);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            log.info("Shutting down gracefully...");
            pizzeria.shutdown();
            log.info("Graceful shutdown completed.");
        }));
    }

}
