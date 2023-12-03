package com.lpnu.PZ.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Pizza {
    private PizzaType pizzaType;
    private double price;
    private List<String> ingredients;

    public Pizza(PizzaType pizzaType, double price, List<String> ingredients) {
        this.pizzaType = pizzaType;
        this.price = price;
        this.ingredients = ingredients;
    }

    public int getAdjustedTime() {
        return this.pizzaType.getMinutesToCreate();
    }

    public int calculateAdjustedTime(int userMinimumTime) {
        if (userMinimumTime < 10) {
            throw new IllegalArgumentException("Pizza can not be created in less than 10 minutes");
        }
        //todo implement the functionality of assigning userMinimumTime to Pizza with lowest time to create
        // and adjust all the other Pizza types accordingly
        return this.getPizzaType().getMinutesToCreate();
    }

    public String toString() {
        return String.format("Pizza: %s, Price: %.2f, Prepare Time: %d minutes",
                pizzaType.name(), price, pizzaType.getMinutesToCreate()); //String.join(", ", ingredients
    }

}
