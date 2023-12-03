package com.lpnu.PZ.services;

import com.lpnu.PZ.domain.Cook;
import com.lpnu.PZ.domain.Order;
import com.lpnu.PZ.domain.OrderMode;
import com.lpnu.PZ.domain.Pizza;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

import java.util.Optional;
import java.util.concurrent.*;
import java.util.stream.Collectors;

@Slf4j
public class Kitchen {
    private final ExecutorService cookThreadPool;
    private final List<Cook> cooks;

    public Kitchen(int numberOfCooks) {
        this.cookThreadPool = Executors.newFixedThreadPool(numberOfCooks);
        this.cooks = new ArrayList<>();

        for (int i = 0; i < numberOfCooks; i++) {
            Cook cook = new Cook();
            cooks.add(cook);
        }
    }

    public CompletableFuture<String> processOrder(final Order order) {
        int pizzasDoneCounter = 0;
        int numberOfPizzasInOrder = order.getPizzas().size();

        CompletableFuture<String> orderCompletableFuture = new CompletableFuture<>();
        List<CompletableFuture<String>> pizzaFutures = new ArrayList<>();


        while (numberOfPizzasInOrder > pizzasDoneCounter) {
            Cook cook = getAvailableCook();
            Pizza pizza = order.getPizzas().get(pizzasDoneCounter++);

            cook.setPizza(pizza);
            this.cookThreadPool.submit(cook);

            pizzaFutures.add(cook.getPizzaCompletableFuture());
        }

        CompletableFuture<Void> allOf = CompletableFuture.allOf(
                pizzaFutures.toArray(new CompletableFuture[0])
        );

        allOf.thenApply(result -> {
            List<String> pizzaResults = pizzaFutures.stream()//
                    .map(CompletableFuture::join)//
                    .collect(Collectors.toList());

            String combinedResult = String.join("\n", pizzaResults);

            orderCompletableFuture.complete(combinedResult);

            return null; //just for chain
        });

        return orderCompletableFuture;
    }

    private Cook getAvailableCook() {
        Optional<Cook> availableCook;

        while (true) {
            availableCook = cooks.stream()
                    .filter(cook -> !cook.isWorking())
                    .findAny();

            if (availableCook.isPresent()) {
                break;
            } else {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return availableCook.orElseThrow();
    }

    private void processOrderTask(Order order, OrderMode orderMode) {
        //to be implemented
    }

//    public void shutdown() {
//        cookThreadPool.shutdown();
//    }
}

