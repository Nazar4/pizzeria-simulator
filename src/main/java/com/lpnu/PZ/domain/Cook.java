package com.lpnu.PZ.domain;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@Slf4j
public class Cook implements Runnable {
    private Pizza pizza;
    @Getter
    private CookState cookState;
    @Getter
    private CompletableFuture<String> pizzaCompletableFuture;
    @Getter
    private boolean isWorking = false;
    private boolean stopped;
    private CountDownLatch pizzaLatch;

    public Cook() {
        this.cookState = CookState.ASSEMBLING;
        this.pizzaLatch = new CountDownLatch(1);
        this.stopped = false;
        pizzaCompletableFuture = new CompletableFuture<>();
    }

    @Override
    public void run() {
        if (stopped) {
            try {
                pizzaLatch.await();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        processPizza();
    }

    public void processPizza() {
        if (pizza == null) {
            throw new IllegalStateException("Pizza must be set before processing.");
        }
        isWorking = true;

        for (CookState state : CookState.values()) {
            cookState = state;
            simulateProcessingTime(pizza.getAdjustedTime() / CookState.values().length);
        }

        log.info("Cook has completed pizza: " + pizza);
        isWorking = false;
        pizzaCompletableFuture.complete("Pizza " + pizza.getPizzaType() + " is completed");
    }

    private void simulateProcessingTime(int timeInSeconds) {
        try {
            TimeUnit.SECONDS.sleep(timeInSeconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void setPizza(Pizza pizza) {
        this.pizza = pizza;
    }

    public void stopCook() {
        stopped = true;
    }

    public void resumeCook() {
        stopped = false;
        pizzaLatch.countDown();
    }
}

