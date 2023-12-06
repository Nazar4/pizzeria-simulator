package com.lpnu.PZ.domain.pizza.state;

import com.lpnu.PZ.domain.Pizza;

public class PreparingToppingsState extends PizzaState {

    public PreparingToppingsState(final Pizza pizza) {
        super(pizza);
    }

    @Override
    public void moveNextState() {
        this.pizza.changeState(new BakingState(this.pizza));
    }

    @Override
    public double getCompletion() {
        return 0.2;
    }
}
