package com.lpnu.PZ.domain.pizza.state;

import com.lpnu.PZ.domain.Pizza;

public class DoneState extends PizzaState {

    public DoneState(final Pizza pizza) {
        super(pizza);
    }

    @Override
    public void moveNextState() {
        this.pizza.setPrepared(true);
    }

    @Override
    public double getCompletion() {
        return 0.1;
    }
}
