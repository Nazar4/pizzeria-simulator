package com.lpnu.PZ.domain;

import com.lpnu.PZ.domain.pizza.state.AssemblingState;
import com.lpnu.PZ.domain.pizza.state.PizzaState;
import com.lpnu.PZ.utils.GlobalConstants;
import lombok.Getter;
import lombok.Setter;

@Getter
public class Pizza {
    private final PizzaType pizzaType;
    private PizzaState pizzaState;
    private double adjustedTimeToCreate;
    @Setter
    private boolean isPrepared;

    public Pizza(final PizzaType pizzaType) {
        this.pizzaType = pizzaType;
        this.isPrepared = false;
        this.pizzaState = new AssemblingState(this);
    }

    public void changeState(final PizzaState state) {
        this.pizzaState = state;
    }

    public void setAdjustedTime(int userMinimumTime) {
        if (userMinimumTime < GlobalConstants.MINIMUM_TIME_TO_CREATE_PIZZA) {
            throw new IllegalArgumentException("Pizza cannot be created in less than 10 minutes");
        }

        this.adjustedTimeToCreate = Math.round(pizzaType.getTimeComplexity() * userMinimumTime);
    }

    @Override
    public String toString() {
        return String.format("Pizza: %s, Price: %.2f, Prepare Time: %.2f minutes, Prepared: %b",
                pizzaType.name(), pizzaType.getPrice(), this.getAdjustedTimeToCreate(), isPrepared);
    }
}
