package com.lpnu.PZ.services;

import com.lpnu.PZ.domain.Cook;
import com.lpnu.PZ.domain.Order;
import com.lpnu.PZ.domain.OrderMode;
import com.lpnu.PZ.domain.Pizza;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

import java.util.concurrent.*;

@Slf4j
public class Kitchen {
    private final int numberOfCooks;
    private final ExecutorService cookThreadPool;
    private final List<Cook> cooks;

    public Kitchen(int numberOfCooks) {
        this.numberOfCooks = numberOfCooks;
        this.cookThreadPool = Executors.newFixedThreadPool(numberOfCooks);
        this.cooks = new ArrayList<>();

        for (int i = 0; i < numberOfCooks; i++) {
            Cook cook = new Cook();
            cooks.add(cook);
        }
    }

    public CompletableFuture<Void> processOrder(Order order) {
        CompletableFuture<Void> orderCompletableFuture = new CompletableFuture<>();

        List<Cook> cooks = getCooksForOrder(order);
        List<CompletableFuture<Void>> pizzaFutures = new ArrayList<>();

        for (int i = 0; i < cooks.size(); i++) {
            Cook cook = cooks.get(i);
            Pizza pizza = order.getPizzas().get(i);

            cook.setPizzaAndStart(pizza);

            CompletableFuture<Void> pizzaFuture = cook.getPizzaCompletableFuture();
            pizzaFutures.add(pizzaFuture);
        }

        CompletableFuture<Void>[] pizzaFuturesArray = new CompletableFuture[pizzaFutures.size()];
        pizzaFutures.toArray(pizzaFuturesArray);

        CompletableFuture<Void> allOf = CompletableFuture.allOf(pizzaFuturesArray);

        allOf.thenRun(() -> {
            log.info("Order completed " + order);
            orderCompletableFuture.complete(null);
        });

        return orderCompletableFuture;
    }

    private List<Cook> getCooksForOrder(Order order) {
        return new ArrayList<>(cooks.subList(0, order.getPizzas().size()));
    }

    private void processOrderTask(Order order, OrderMode orderMode) {
        //to be implemented
    }

    public void shutdown() {
        cookThreadPool.shutdown();
    }
}

