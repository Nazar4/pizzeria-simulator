package com.lpnu.PZ.services;

import com.lpnu.PZ.domain.Order;
import com.lpnu.PZ.dto.CookDTO;
import com.lpnu.PZ.dto.PizzeriaConfigurationDTO;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Component
@NoArgsConstructor
@Slf4j
public class Pizzeria {
    private Kitchen kitchen;
    private ClientGenerationStrategy clientGenerationStrategy;
    private ExecutorService orderProcessorThreadPool;

    public void configurePizzeria(final PizzeriaConfigurationDTO configuration) {
        this.kitchen = Kitchen.getInstance(configuration.getCooksNumber());
        this.clientGenerationStrategy = configuration.isRandomGenerationStrategy()
                ? new RandomGenerationStrategy(configuration.getPizzasNumber(), configuration.getMinimalPizzaCreationTime())
                : new IntervalGenerationStrategy(configuration.getPizzasNumber(), configuration.getMinimalPizzaCreationTime());
        this.orderProcessorThreadPool = Executors.newFixedThreadPool(2);
        runPizzeria();
    }

    public void runPizzeria() {
        clientGenerationStrategy.generateClientWithInterval();

        orderProcessorThreadPool.submit(() -> processOrders(clientGenerationStrategy.getPaydesk().getOrdinaryQueue()));
        orderProcessorThreadPool.submit(() -> processOrders(clientGenerationStrategy.getPaydesk().getPriorityQueue()));
    }

    private void processOrders(final Queue<Order> queue) {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                if (kitchen.hasAvailableCook()) { //to address backpressure issue
                    final Order order = queue.poll();
                    if (order != null) {
                        CompletableFuture<Order> result = kitchen.processOrder(order);

                        result.thenAccept(res -> log.info("Order Processed: \n{}", res));
                    }
                }

                TimeUnit.MILLISECONDS.sleep(1000);
            } catch (InterruptedException e) {
                log.error("Error in order processing thread: {}", e.getMessage());
                Thread.currentThread().interrupt();
            }
        }
    }

    public List<CookDTO> getCooks() {
        return kitchen.getCooks().stream()//
                .map(x -> new CookDTO(x.getCookId(), x.getCookState()))//
                .collect(Collectors.toList());
    }

    public Optional<CookDTO> getCookById(String cookId) {
        return getCooks().stream()//
                .filter(x -> x.getCookId().equals(cookId))//
                .findFirst();
    }


    public boolean stopCookById(String cookId) {
        return this.kitchen.stopCookById(cookId);
    }

    public boolean resumeCookById(String cookId) {
        return this.kitchen.resumeCookById(cookId);
    }

    public void shutdown() {
        this.orderProcessorThreadPool.shutdown();
        this.kitchen.shutdown();
        this.clientGenerationStrategy.shutdown();
    }

}
