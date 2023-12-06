package com.lpnu.PZ.services;

import com.lpnu.PZ.domain.Client;
import com.lpnu.PZ.domain.Order;

public class RandomGenerationStrategy extends ClientGenerationStrategy {
    public static int clientId = 0;

    public RandomGenerationStrategy(final int numberOfPizzasInMenu, final int minimumTimeToCreatePizza) {
        super(numberOfPizzasInMenu, minimumTimeToCreatePizza);
    }

    @Override
    public Client generateClient() {
        Order order = generateRandomOrder();
        return new Client("Client_" + (clientId++), order);
    }
}