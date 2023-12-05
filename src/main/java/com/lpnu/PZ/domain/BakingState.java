package com.lpnu.PZ.domain;

public class BakingState extends PizzaState{
    public BakingState(Pizza pizza) {
        super(pizza);
    }

    @Override
    void moveNextState() {

    }

    @Override
    double getCompletion() {
        return 0;
    }
}
