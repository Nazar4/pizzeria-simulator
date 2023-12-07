package com.lpnu.PZ.domain;

import com.lpnu.PZ.domain.pizza.state.DoneState;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@Slf4j
public class Cook implements Runnable {
    private Pizza pizza;
    @Getter
    private final String cookId;
    @Getter
    private CookState cookState;
    @Getter
    private final CompletableFuture<Pizza> pizzaCompletableFuture;
    @Getter
    @Setter
    private boolean isWorking;
    private boolean stopped;
    private final CountDownLatch pizzaLatch;

    public Cook() {
        this.cookState = CookState.COOKING;
        this.pizzaLatch = new CountDownLatch(1);
        this.isWorking = false;
        this.stopped = false;
        this.cookId = "Cook" + "_" + Instant.now().toEpochMilli();;
        pizzaCompletableFuture = new CompletableFuture<>();
    }

    @Override
    public void run() {
        isWorking = true;
        if (stopped) {
            try {
                pizzaLatch.await();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        processPizza();
        isWorking = false;
    }

    public void processPizza() {
        if (pizza == null) {
            throw new IllegalStateException("Pizza must be set before processing.");
        }

        while (!this.pizza.isPrepared()) {
            simulateProcessingTime(pizza.getAdjustedTimeToCreate() * this.pizza.getPizzaState().getCompletion());
            this.pizza.getPizzaState().moveNextState();
        }

        log.info("Cook has completed pizza: " + pizza);
        pizzaCompletableFuture.complete(pizza);
    }

    private void simulateProcessingTime(double timeInSeconds) {
        try {
            TimeUnit.MILLISECONDS.sleep((long) (timeInSeconds * 1000L));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void setPizza(final Pizza pizza) {
        this.pizza = pizza;
    }

    public void stopCook() {
        this.cookState = CookState.ON_BREAK;
        stopped = true;
    }

    public void resumeCook() {
        if (stopped) {
            this.cookState = CookState.COOKING;
            stopped = false;
            pizzaLatch.countDown();
        }
    }
}

