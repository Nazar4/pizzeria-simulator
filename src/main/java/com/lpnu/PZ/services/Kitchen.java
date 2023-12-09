package com.lpnu.PZ.services;

import com.lpnu.PZ.domain.Cook;
import com.lpnu.PZ.domain.Order;
import com.lpnu.PZ.domain.OrderState;
import com.lpnu.PZ.domain.Pizza;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Slf4j
public class Kitchen {
    private final ExecutorService cookThreadPool;
    private final List<Cook> cooks;

    private static volatile Kitchen instance;

    private Kitchen(int numberOfCooks) {
        this.cookThreadPool = Executors.newFixedThreadPool(numberOfCooks);
        this.cooks = new ArrayList<>();

        for (int i = 0; i < numberOfCooks; i++) {
            Cook cook = new Cook();
            cooks.add(cook);
        }
    }

    public static Kitchen getInstance(int numberOfCooks) {
        Kitchen result = instance;
        if (result != null) {
            return result;
        }
        synchronized (Kitchen.class) {
            if (instance == null) {
                instance = new Kitchen(numberOfCooks);
            }
            return instance;
        }
    }

    public CompletableFuture<Order> processOrder(final Order order) {
        CompletableFuture<Order> orderCompletableFuture = new CompletableFuture<>();
        List<CompletableFuture<Pizza>> pizzaFutures = new ArrayList<>();

        order.setOrderState(OrderState.PREPARING_ORDER);
        for (final Pizza pizza : order.getPizzas()) {
            Cook cook = getAvailableCook();

            cook.setPizza(pizza);
            this.cookThreadPool.submit(cook);

            pizzaFutures.add(cook.getPizzaCompletableFuture());
        }

        return CompletableFuture.allOf(
                pizzaFutures.toArray(new CompletableFuture[0])
        ).thenApply(result -> {
            order.setOrderState(OrderState.ORDER_FINISHED);
            orderCompletableFuture.complete(order);
            return order; //for chain
        });
    }

    private synchronized Cook getAvailableCook() {
        Optional<Cook> availableCookOptional;
        Cook availableCook;

        while (!Thread.currentThread().isInterrupted()) {
            availableCookOptional = cooks.stream()
                    .filter(cook -> !cook.isWorking())
                    .findAny();

            if (availableCookOptional.isPresent()) {
                availableCook = availableCookOptional.get();
                availableCook.setWorking(true);
                return availableCook;
            } else {
                try {
                    TimeUnit.MILLISECONDS.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        throw new IllegalStateException("Unhandled situation to recover");
    }

    public boolean hasAvailableCook() {
        return cooks.stream().anyMatch(cook -> !cook.isWorking());
    }

    public void shutdown() {
        cookThreadPool.shutdown();
    }

    public boolean stopCookById(final String cookId) {
        Optional<Cook> cookOptional = this.cooks.stream()
                .filter(cook -> cook.getCookId().equals(cookId))
                .findFirst();

        if (cookOptional.isPresent()) {
            cookOptional.get().stopCook();
            return true;
        }

        return false;
    }

    public boolean resumeCookById(final String cookId) {
        Optional<Cook> cookOptional = this.cooks.stream()
                .filter(cook -> cook.getCookId().equals(cookId))
                .findFirst();

        if (cookOptional.isPresent()) {
            cookOptional.get().resumeCook();
            return true;
        }

        return false;
    }
}

