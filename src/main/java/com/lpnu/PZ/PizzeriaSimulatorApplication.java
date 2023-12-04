package com.lpnu.PZ;

import com.lpnu.PZ.domain.Order;
import com.lpnu.PZ.domain.Pizza;
import com.lpnu.PZ.domain.PizzaType;
import com.lpnu.PZ.services.Kitchen;
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

        Kitchen kitchen = new Kitchen(1);
        CompletableFuture<String> voidCompletableFuture = kitchen.processOrder(new Order(List.of(new Pizza(PizzaType.HAWAIIAN)), false));
        voidCompletableFuture.thenAccept(log::info);
    }

}
