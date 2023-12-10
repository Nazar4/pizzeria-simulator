package com.lpnu.PZ.domain;

import com.lpnu.PZ.domain.pizza.state.AssemblingState;
import com.lpnu.PZ.domain.pizza.state.PizzaState;
import com.lpnu.PZ.utils.GlobalConstants;
import lombok.Getter;
import lombok.Setter;

import java.time.format.DateTimeFormatter;

@Getter
public class Pizza {
    private final PizzaType pizzaType;
    private PizzaState pizzaState;
    private final PizzaLog log;
    private double adjustedTimeToCreate;
    @Setter
    private boolean isPrepared;
    @Setter
    private boolean partialProcessing;

    public Pizza(final PizzaType pizzaType) {
        this.pizzaType = pizzaType;
        this.isPrepared = false;
        this.partialProcessing = false;
        this.log = new PizzaLog();
        this.pizzaState = new AssemblingState(this);
    }

    public void changeState(final PizzaState state) {
        this.pizzaState = state;
    }

    public void setAdjustedTime(final int userMinimumTime) {
        if (userMinimumTime < GlobalConstants.MINIMUM_TIME_TO_CREATE_PIZZA) {
            throw new IllegalArgumentException("Pizza cannot be created in less than 10 seconds");
        }

        this.adjustedTimeToCreate = Math.round(pizzaType.getTimeComplexity() * userMinimumTime);
    }

    @Override
    public String toString() {
        return String.format("Pizza: %s, Price: %.2f, Prepared: %b, startPreparing: %s, endPreparing: %s",
                pizzaType.name(), pizzaType.getPrice(), isPrepared,
                log.getStartDate().format(DateTimeFormatter.ofPattern(GlobalConstants.PIZZA_LOG_FORMAT)),
                log.getEndDate().format(DateTimeFormatter.ofPattern(GlobalConstants.PIZZA_LOG_FORMAT)));
    }
}
