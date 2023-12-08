package com.lpnu.PZ.services;

import com.lpnu.PZ.domain.Order;
import com.lpnu.PZ.dto.PizzeriaConfigurationDTO;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Queue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Component
@NoArgsConstructor
@Slf4j
public class Pizzeria {
    private Kitchen kitchen;
    private ClientGenerationStrategy clientGenerationStrategy;

    public void configurePizzeria(final PizzeriaConfigurationDTO configuration) {
        this.kitchen = Kitchen.getInstance(configuration.getCooksNumber());
        this.clientGenerationStrategy = configuration.isRandomGenerationStrategy()
                ? new RandomGenerationStrategy(configuration.getPizzasNumber(), configuration.getMinimalPizzaCreationTime())
                : new IntervalGenerationStrategy(configuration.getPizzasNumber(), configuration.getMinimalPizzaCreationTime());
        runPizzeria();
    }

    public void runPizzeria() {
        final ExecutorService orderProcessorThreadPool = Executors.newFixedThreadPool(2);

        clientGenerationStrategy.generateClientWithInterval();

        orderProcessorThreadPool.submit(() -> processOrders(clientGenerationStrategy.getPaydesk().getOrdinaryQueue()));
        orderProcessorThreadPool.submit(() -> processOrders(clientGenerationStrategy.getPaydesk().getPriorityQueue()));
    }

    private void processOrders(final Queue<Order> queue) {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                final Order order = queue.poll();
                if (order != null) {
                    CompletableFuture<Order> result = kitchen.processOrder(order);

                    result.thenAccept(res -> log.info("Order Processed: \n{}", res));
                }

                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e) {
                log.error("Error in order processing thread: {}", e.getMessage());
                Thread.currentThread().interrupt();
            }
        }
    }

    public boolean stopCookById(String cookId) {
        return this.kitchen.stopCookById(cookId);
    }

    public boolean resumeCookById(String cookId) {
        return this.kitchen.resumeCookById(cookId);
    }
}
