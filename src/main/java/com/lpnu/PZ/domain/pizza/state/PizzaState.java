package com.lpnu.PZ.domain.pizza.state;

import com.lpnu.PZ.domain.Pizza;
import lombok.Getter;

public abstract class PizzaState {
    protected Pizza pizza;
    @Getter
    protected CookOperation cookOperation;

    public PizzaState(final Pizza pizza) {
        this.pizza = pizza;
    }

    public abstract void moveNextState();

    public abstract double getCompletion();
}
