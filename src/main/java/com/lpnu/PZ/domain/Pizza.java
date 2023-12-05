package com.lpnu.PZ.domain;

import com.lpnu.PZ.utils.GlobalConstants;
import lombok.Getter;

import java.util.Arrays;
import java.util.Comparator;

@Getter
public class Pizza {
    private final PizzaType pizzaType;

    public PizzaState getPizzaState() {
        return pizzaState;
    }

    private PizzaState pizzaState;
    private int adjustedTimeToCreate;

    public Pizza(final PizzaType pizzaType) {
        this.pizzaType = pizzaType;
        this.pizzaState = new AssemblingState(this);
    }

    public void changeState(PizzaState state) {
        this.pizzaState = state;
    }

    public void setAdjustedTime(int userMinimumTime) {
        if (userMinimumTime < GlobalConstants.MINIMUM_TIME_TO_CREATE_PIZZA) {
            throw new IllegalArgumentException("Pizza cannot be created in less than 10 minutes");
        }

        this.adjustedTimeToCreate = (int) Math.round(pizzaType.getTimeComplexity() * userMinimumTime);
    }

    public String toString() {
        return String.format("Pizza: %s, Price: %.2f, Prepare Time: %d minutes",
                pizzaType.name(), pizzaType.getPrice(), this.getAdjustedTimeToCreate());
    }
}
