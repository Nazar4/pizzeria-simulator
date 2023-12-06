package com.lpnu.PZ.services;

import com.lpnu.PZ.domain.Client;
import com.lpnu.PZ.domain.Order;
import com.lpnu.PZ.dto.PizzeriaConfigurationDTO;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Queue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;

@Component
@NoArgsConstructor
@Slf4j
public class Pizzeria {
    private Kitchen kitchen;
    private Paydesk paydesk;
    private ClientGenerationStrategy clientGenerationStrategy;

    public void configurePizzeria(final PizzeriaConfigurationDTO configuration) {
        this.kitchen = Kitchen.getInstance(configuration.getCooksNumber());
        this.clientGenerationStrategy = configuration.isRandomGenerationStrategy()
                ? new RandomGenerationStrategy(configuration.getPizzasNumber(), configuration.getMinimalPizzaCreationTime())
                : new IntervalGenerationStrategy(configuration.getPizzasNumber(), configuration.getMinimalPizzaCreationTime());
        this.paydesk = new Paydesk();
        runPizzeria();
    }

    public void runPizzeria() {
        ExecutorService clientGeneratorThreadPool = Executors.newSingleThreadExecutor();
        ExecutorService orderProcessorThreadPool = Executors.newFixedThreadPool(2);

        clientGeneratorThreadPool.submit(() -> {
            while (true) {
                try {
                    final Client client = clientGenerationStrategy.generateClient();
                    paydesk.getClients().add(client);

                    if (ThreadLocalRandom.current().nextBoolean()) {
                        paydesk.getOrdinaryQueue().add(client.getOrder());
                    } else {
                        paydesk.getPriorityQueue().add(client.getOrder());
                    }

                    int sleepTime = clientGenerationStrategy instanceof RandomGenerationStrategy
                            ? ThreadLocalRandom.current().nextInt(10, 21)
                            : 15;

                    Thread.sleep(sleepTime * 1000L);
                } catch (InterruptedException e) {
                    log.error("Error in client generation thread: {}", e.getMessage());
                    Thread.currentThread().interrupt();
                }
            }
        });

        orderProcessorThreadPool.submit(() -> processOrders(paydesk.getOrdinaryQueue()));
        orderProcessorThreadPool.submit(() -> processOrders(paydesk.getPriorityQueue()));
    }

    private void processOrders(final Queue<Order> queue) {
        while (true) {
            try {
                final Order order = queue.poll();
                if (order != null) {
                    CompletableFuture<String> result = kitchen.processOrder(order);

                    result.thenAcceptAsync(res -> log.info("Order Processed: {}", res));
                }

                Thread.sleep(100);
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
