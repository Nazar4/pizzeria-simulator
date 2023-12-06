package com.lpnu.PZ.domain.pizza.state;

import com.lpnu.PZ.domain.Pizza;

public abstract class PizzaState {
    protected Pizza pizza;

    public PizzaState(final Pizza pizza) {
        this.pizza = pizza;
    }

    public abstract void moveNextState();

    public abstract double getCompletion();
}
