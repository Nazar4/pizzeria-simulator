package com.lpnu.PZ.services;

import com.lpnu.PZ.domain.Cook;
import com.lpnu.PZ.domain.Order;
import com.lpnu.PZ.domain.OrderMode;
import com.lpnu.PZ.domain.OrderState;
import com.lpnu.PZ.domain.Pizza;
import com.lpnu.PZ.domain.pizza.state.CookOperation;
import com.lpnu.PZ.utils.GlobalConstants;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

@Slf4j
public class Kitchen {
    private final ExecutorService cookThreadPool;
    @Getter
    private final List<Cook> cooks;

    private static volatile Kitchen instance;

    private Kitchen(final int numberOfCooks) {
        this.cookThreadPool = Executors.newFixedThreadPool(numberOfCooks);
        this.cooks = new ArrayList<>();

        if (numberOfCooks >= GlobalConstants.MIN_COOKS_NUMBER) {
            for (int i = 0; i < numberOfCooks; i++) {
                final Cook cook = new Cook(CookOperation.values()[i % GlobalConstants.MIN_COOKS_NUMBER]);
                cooks.add(cook);
            }
        } else {
            throw new IllegalArgumentException(String.format("Number of cooks has to be at least 5, got %d", numberOfCooks));
        }
    }

    public static Kitchen getInstance(final int numberOfCooks) {
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
        if (OrderMode.PARTIAL_PROCESSING.equals(order.getOrderMode())) {
            return processPartialOrder(order);
        }
        final CompletableFuture<Order> orderCompletableFuture = new CompletableFuture<>();
        final List<CompletableFuture<Pizza>> pizzaFutures = new ArrayList<>();

        order.setOrderState(OrderState.PREPARING_ORDER);
        for (final Pizza pizza : order.getPizzas()) {
            final Cook cook = getAvailableCook();

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

    private CompletableFuture<Order> processPartialOrder(final Order order) {
        final CompletableFuture<Order> orderCompletableFuture = new CompletableFuture<>();
        final List<CompletableFuture<Void>> pizzaFutures = new ArrayList<>();

        order.setOrderState(OrderState.PREPARING_ORDER);
        for (final Pizza pizza : order.getPizzas()) {
            processPizzaPartially(pizza, pizzaFutures);
        }

        return CompletableFuture.allOf(pizzaFutures.toArray(new CompletableFuture[0]))
                .thenApply(result -> {
                    order.setOrderState(OrderState.ORDER_FINISHED);
                    orderCompletableFuture.complete(order);
                    return order; // for chaining
                });
    }

    private void processPizzaPartially(final Pizza pizza, List<CompletableFuture<Void>> pizzaFutures) {
        final CompletableFuture<Void> pizzaCompletableFuture = CompletableFuture.runAsync(() -> {
            while (!pizza.isPrepared()) {
                final Cook cook = getAvailableCookForOperation(pizza.getPizzaState().getCookOperation());
                cook.setPizza(pizza);

                final Future<?> future = this.cookThreadPool.submit(cook);
                try {
                    future.get(); // Wait for the cook task to complete
                } catch (final InterruptedException | ExecutionException e) {
                    Thread.currentThread().interrupt();
                    throw new RuntimeException("Error waiting for cook to process pizza", e);
                }
            }
        });
        pizzaFutures.add(pizzaCompletableFuture);
    }

    private synchronized Cook getAvailableCookForOperation(final CookOperation operation) {
        Optional<Cook> availableCookOptional;
        Cook availableCook;

        while (!Thread.currentThread().isInterrupted()) {
            availableCookOptional = cooks.stream()
                    .filter(cook -> !cook.isWorking() && cook.getCookOperation().equals(operation))
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
        final Optional<Cook> cookOptional = this.cooks.stream()
                .filter(cook -> cook.getCookId().equals(cookId))
                .findFirst();

        if (cookOptional.isPresent()) {
            cookOptional.get().stopCook();
            return true;
        }

        return false;
    }

    public boolean resumeCookById(final String cookId) {
        final Optional<Cook> cookOptional = this.cooks.stream()
                .filter(cook -> cook.getCookId().equals(cookId))
                .findFirst();

        if (cookOptional.isPresent()) {
            cookOptional.get().resumeCook();
            return true;
        }

        return false;
    }
}

