package com.lpnu.PZ.domain;

public class MakingDoughState extends PizzaState{
    public MakingDoughState(Pizza pizza) {
        super(pizza);
    }

    @Override
    void moveNextState() {
        this.pizza.changeState(new PreparingToppingsState(this.pizza));
    }

    @Override
    double getCompletion() {
        return 0.2;
    }
}
