package com.lpnu.PZ.services;

import com.lpnu.PZ.domain.Client;
import com.lpnu.PZ.domain.Order;
import com.lpnu.PZ.domain.Pizza;
import com.lpnu.PZ.domain.PizzaType;
import com.lpnu.PZ.utils.GlobalConstants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public abstract class ClientGenerationStrategy {
    private List<PizzaType> menu;
    private final int minimumTimeToCreatePizza;

    public ClientGenerationStrategy(final int numberOfPizzasInMenu, final int minimumTimeToCreatePizza) {
        this.menu = new ArrayList<>();
        this.minimumTimeToCreatePizza = minimumTimeToCreatePizza;
        fillMenuRandomly(numberOfPizzasInMenu);
    }

    public abstract Client generateClient();

    private void fillMenuRandomly(int numberOfPizzas) {
        List<PizzaType> allPizzaTypes = new ArrayList<>(List.of(PizzaType.values()));

        if (numberOfPizzas > allPizzaTypes.size()) {
            throw new IllegalArgumentException("Number of pizzas in the menu exceeds the available pizza types");
        }

        Collections.shuffle(allPizzaTypes);
        this.menu = new ArrayList<>(allPizzaTypes.subList(0, numberOfPizzas));;
    }

    public Order generateRandomOrder() {
        int numberOfPizzas = ThreadLocalRandom.current().nextInt(1, GlobalConstants.MAXIMAL_NUMBER_OF_PIZZAS_PER_ORDER);
        List<Pizza> pizzas = new ArrayList<>();

        for (int i = 0; i < numberOfPizzas; i++) {
            final PizzaType randomPizzaType = this.menu.get(ThreadLocalRandom.current().nextInt(menu.size()));
            final Pizza pizza = new Pizza(randomPizzaType);
            pizza.setAdjustedTime(this.minimumTimeToCreatePizza);
            pizzas.add(pizza);
        }

        return new Order(pizzas, ThreadLocalRandom.current().nextBoolean());
    }

}
