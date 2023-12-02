package com.lpnu.PZ.services;

import com.lpnu.PZ.domain.Client;
import com.lpnu.PZ.domain.Order;
import com.lpnu.PZ.domain.Pizza;
import com.lpnu.PZ.domain.PizzaType;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ThreadLocalRandom;

public class RegularGenerationStrategy extends ClientGenerationStrategy {
    private final int intervalMinutes;

    public RegularGenerationStrategy(int intervalMinutes) {
        this.intervalMinutes = intervalMinutes;
    }

    private void startGeneration() {
        Timer timer = new Timer(true);
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
            }
        }, 0, (long) intervalMinutes * 60 * 1000);
    }

    @Override
    public List<Client> generateClients() {
        List<Client> clients = new ArrayList<>();

        List<PizzaType> menu = new ArrayList<>(List.of(PizzaType.values()));

        int numberOfClients = ThreadLocalRandom.current().nextInt(1, 6);

        for (int i = 0; i < numberOfClients; i++) {
            Order order = generateRandomOrder(menu);
            Client client = new Client("Client-" + (i + 1), order);
            clients.add(client);
        }

        return clients;
    }

    private Order generateRandomOrder(List<PizzaType> menu) {
        int numberOfPizzas = ThreadLocalRandom.current().nextInt(1, 4);
        List<Pizza> pizzas = new ArrayList<>();

        for (int i = 0; i < numberOfPizzas; i++) {
            PizzaType randomPizzaType = menu.get(ThreadLocalRandom.current().nextInt(menu.size()));
            //need to implement functionality of ingredients or discard it
//            pizzas.add(new Pizza(randomPizzaType, ));
        }

        return new Order(pizzas, ThreadLocalRandom.current().nextBoolean());
    }
}
