package com.lpnu.PZ.domain;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

@Slf4j
public class Cook implements Runnable {
    private Pizza pizza;
    @Getter
    private String cookId;
    @Getter
    private CookState cookState;
    @Getter
    private CompletableFuture<String> pizzaCompletableFuture;
    @Getter
    private boolean isWorking = false;
    private boolean stopped;
    private CountDownLatch pizzaLatch;

    public Cook() {
        this.cookState = CookState.COOKING;
        this.pizzaLatch = new CountDownLatch(1);
        this.stopped = false;
        this.cookId = "Cook" + "_" + System.currentTimeMillis();
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

        while (!(this.pizza.getPizzaState() instanceof DoneState)) {
            this.pizza.getPizzaState().moveNextState();
            simulateProcessingTime(pizza.getAdjustedTimeToCreate() / 5);
        }

        log.info("Cook has completed pizza: " + pizza);
        isWorking = false;
        pizzaCompletableFuture.complete("Pizza " + pizza.getPizzaType() + " is completed");
    }

    private void simulateProcessingTime(int timeInSeconds) {
        try {
            TimeUnit.MILLISECONDS.sleep(timeInSeconds * 1000L);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void setPizza(Pizza pizza) {
        this.pizza = pizza;
    }

    public void stopCook() {
        this.cookState = CookState.ON_BREAK;
        stopped = true;
    }

    public void resumeCook() {
        if(stopped){
            this.cookState = CookState.COOKING;
            stopped = false;
            pizzaLatch.countDown();
        }
    }
}

