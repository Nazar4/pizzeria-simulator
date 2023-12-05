package com.lpnu.PZ.domain;

public class PreparingToppingsState extends PizzaState{
    public PreparingToppingsState(Pizza pizza) {
        super(pizza);
    }

    @Override
    void moveNextState() {
        this.pizza.changeState(new BakingState(this.pizza));
    }

    @Override
    double getCompletion() {
        return 0.5;
    }
}
