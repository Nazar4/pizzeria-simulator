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
import java.util.concurrent.TimeUnit;

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
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    final Client client = clientGenerationStrategy.generateClient();
                    paydesk.getClients().add(client);

                    if (client.getOrder().getPriority() > 0) {
                        paydesk.getPriorityQueue().add(client.getOrder());
                    } else {
                        paydesk.getOrdinaryQueue().add(client.getOrder());
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
