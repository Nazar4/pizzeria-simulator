package com.lpnu.PZ.domain;

public class AssemblingState extends PizzaState {
    public AssemblingState(Pizza pizza) {
        super(pizza);
    }

    @Override
    void moveNextState() {
        this.pizza.changeState(new MakingDoughState(this.pizza));
    }

    @Override
    double getCompletion() {
        return 0.1;
    }
}
