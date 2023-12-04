package com.lpnu.PZ.domain;

import lombok.Getter;

import java.util.Arrays;
import java.util.Comparator;

public class Pizza {
    @Getter
    private PizzaType pizzaType;
    @Getter
    private PizzaState pizzaState;
    @Getter
    private int adjustedTimeToCreate;

    private PizzaType pizzaWithLowestTimeToCreate;

    public Pizza(PizzaType pizzaType) {
        this.pizzaType = pizzaType;
        this.pizzaState = PizzaState.ASSEMBLING;
        this.adjustedTimeToCreate = pizzaType.getMinutesToCreate();
    }

    public void setAdjustedTime(int userMinimumTime) {
        if (userMinimumTime < 10) {
            throw new IllegalArgumentException("Pizza cannot be created in less than 10 minutes");
        }

        int difference = getDifference(userMinimumTime);

        for (PizzaType pizzaType : PizzaType.values()) {
            int adjustedTime = Math.max(10, pizzaType.getMinutesToCreate() - difference);
            pizzaType.setMinutesToCreate(adjustedTime);
        }

        this.adjustedTimeToCreate = Math.max(10, this.adjustedTimeToCreate - difference);
    }

    private int getDifference(int userMinimumTime) {
        if (this.pizzaWithLowestTimeToCreate == null) {
            this.pizzaWithLowestTimeToCreate =
                    Arrays.stream(PizzaType.values()).min(Comparator.comparingInt(PizzaType::getMinutesToCreate))
                            .orElseThrow(() -> new IllegalStateException("No Pizza type configured"));
        }

        return pizzaWithLowestTimeToCreate.getMinutesToCreate() - userMinimumTime;
    }

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
