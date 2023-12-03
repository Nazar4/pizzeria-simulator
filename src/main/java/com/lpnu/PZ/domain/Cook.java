package com.lpnu.PZ.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@Slf4j
public class Cook extends Thread {
    private static int cookCounter = 0;
    private static int activeCookCount = 0;

    @Getter
    private CompletableFuture<Void> pizzaCompletableFuture;

    private Pizza pizza;
    @Getter
    private final int cookId;
    @Getter
    @Setter
    private CookState cookState;
    private volatile boolean stopped;

    public Cook() {
        this.cookId = generateCookId();
        this.cookState = CookState.ASSEMBLING;
        this.stopped = false;
        pizzaCompletableFuture = new CompletableFuture<>();
    }

    @Override
    public void run() {
        try {
            activeCookCount++;
            processPizza();
        } finally {
            activeCookCount--;
        }
    }

    public void processPizza() {
        if (pizza == null) {
            throw new IllegalStateException("Pizza must be set before processing.");
        }
        // Simulate even time distribution for each stage
        for (CookState state : CookState.values()) {
            cookState = state;
            simulateProcessingTime(pizza.getAdjustedTime() / CookState.values().length);
        }

        log.info("Cook " + cookId + " has completed pizza: " + pizza);
        pizzaCompletableFuture.complete(null);
    }

    private void simulateProcessingTime(int timeInMillis) {
        try {
            TimeUnit.SECONDS.sleep(timeInMillis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void setPizzaAndStart(Pizza pizza) {
        this.pizza = pizza;
        start();
    }

    public void stopCook() {
        stopped = true;
    }

    private synchronized int generateCookId() {
        return ++cookCounter;
    }
}

