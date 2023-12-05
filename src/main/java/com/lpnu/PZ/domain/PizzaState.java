package com.lpnu.PZ.domain;

public abstract class PizzaState {
    protected Pizza pizza;

    public PizzaState(Pizza pizza) {
        this.pizza = pizza;
    }
    abstract void moveNextState();
    abstract double getCompletion();
}
