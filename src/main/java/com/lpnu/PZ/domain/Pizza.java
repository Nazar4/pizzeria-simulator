package com.lpnu.PZ.domain;

import com.lpnu.PZ.utils.GlobalConstants;
import lombok.Getter;

import java.util.Arrays;
import java.util.Comparator;

@Getter
public class Pizza {
    private final PizzaType pizzaType;
    private PizzaState pizzaState;
    private int adjustedTimeToCreate;

    public Pizza(final PizzaType pizzaType) {
        this.pizzaType = pizzaType;
        this.pizzaState = PizzaState.ASSEMBLING;
    }

    public void setAdjustedTime(int userMinimumTime) {
        if (userMinimumTime < GlobalConstants.MINIMUM_TIME_TO_CREATE_PIZZA) {
            throw new IllegalArgumentException("Pizza cannot be created in less than 10 minutes");
        }

        this.adjustedTimeToCreate = (int) Math.round(pizzaType.getTimeComplexity() * userMinimumTime);
    }

    //in future maybe it will be better to create separate class for pizza state
    public void moveNextState() {
        switch (this.pizzaState) {
            case ASSEMBLING:
                this.pizzaState = PizzaState.MAKING_DOUGH;
                break;
            case MAKING_DOUGH:
                this.pizzaState = PizzaState.PREPARING_TOPPINGS;
                break;
            case PREPARING_TOPPINGS:
                this.pizzaState = PizzaState.BAKING;
                break;
            case BAKING:
                this.pizzaState = PizzaState.DONE;
                break;
            case DONE:
                break;
        }
    }

    public String toString() {
        return String.format("Pizza: %s, Price: %.2f, Prepare Time: %d minutes",
                pizzaType.name(), pizzaType.getPrice(), this.getAdjustedTimeToCreate());
    }
}
