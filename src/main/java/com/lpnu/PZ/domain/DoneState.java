package com.lpnu.PZ.domain;

public class DoneState extends PizzaState{
    public DoneState(Pizza pizza) {
        super(pizza);
    }

    @Override
    void moveNextState() {
    }

    @Override
    double getCompletion() {
        return 0.2;
    }
}
