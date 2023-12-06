package com.lpnu.PZ.domain.pizza.state;

import com.lpnu.PZ.domain.Pizza;

public class BakingState extends PizzaState {

    public BakingState(final Pizza pizza) {
        super(pizza);
    }

    @Override
    public void moveNextState() {
        this.pizza.changeState(new DoneState(this.pizza));
    }

    @Override
    public double getCompletion() {
        return 0.3;
    }
}
