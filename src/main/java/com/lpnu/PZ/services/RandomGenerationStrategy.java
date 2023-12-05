package com.lpnu.PZ.services;

import com.lpnu.PZ.domain.Client;
import com.lpnu.PZ.domain.Order;

public class RandomGenerationStrategy extends ClientGenerationStrategy {

    public static int clientId = 0;

    public RandomGenerationStrategy(final int numberOfPizzasInMenu) {
        super(numberOfPizzasInMenu);
    }

    @Override
    public Client generateClient() {
        Order order = generateRandomOrder();
        return new Client("Client-" + (clientId++), order);
    }
}