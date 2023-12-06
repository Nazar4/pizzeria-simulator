package com.lpnu.PZ;

import com.lpnu.PZ.domain.Order;
import com.lpnu.PZ.domain.Pizza;
import com.lpnu.PZ.domain.PizzaType;
import com.lpnu.PZ.dto.PizzeriaConfigurationDTO;
import com.lpnu.PZ.services.Kitchen;
import com.lpnu.PZ.services.Pizzeria;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Slf4j
@SpringBootApplication
public class PizzeriaSimulatorApplication {

    public static void main(String[] args) {
        SpringApplication.run(PizzeriaSimulatorApplication.class, args);

        final var pizzeriaConfigurationDTO = new PizzeriaConfigurationDTO();
        pizzeriaConfigurationDTO.setCooksNumber(8);
        pizzeriaConfigurationDTO.setPizzasNumber(4);
        pizzeriaConfigurationDTO.setMinimalPizzaCreationTime(13);
        pizzeriaConfigurationDTO.setIntervalGenerationStrategy(true);
        Pizzeria pizzeria = new Pizzeria();
        pizzeria.configurePizzeria(pizzeriaConfigurationDTO);
//        Kitchen kitchen = Kitchen.getInstance(1);
//        CompletableFuture<String> voidCompletableFuture = kitchen.processOrder(new Order(List.of(new Pizza(PizzaType.HAWAIIAN)), false));
//        voidCompletableFuture.thenAccept(log::info);
    }

}
