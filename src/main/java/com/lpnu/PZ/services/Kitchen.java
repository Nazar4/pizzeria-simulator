package com.lpnu.PZ.services;

import com.lpnu.PZ.domain.Cook;
import com.lpnu.PZ.domain.Order;
import com.lpnu.PZ.domain.OrderMode;

import java.util.List;

import java.util.concurrent.*;

public class Kitchen {
    private final int numberOfCooks;
    private final ExecutorService cookThreadPool;

    public Kitchen(int numberOfCooks) {
        this.numberOfCooks = numberOfCooks;
        this.cookThreadPool = Executors.newFixedThreadPool(numberOfCooks);

        for (int i = 0; i < numberOfCooks; i++) {
            Cook cook = new Cook();
            cookThreadPool.execute(cook);
        }
    }

    public void processOrder(Order order, OrderMode orderMode) {
        Runnable orderProcessingTask = () -> processOrderTask(order, orderMode);

        cookThreadPool.submit(orderProcessingTask);
    }

    private void processOrderTask(Order order, OrderMode orderMode) {
        //to be implemented
    }

    public void shutdown() {
        cookThreadPool.shutdown();
    }
}

