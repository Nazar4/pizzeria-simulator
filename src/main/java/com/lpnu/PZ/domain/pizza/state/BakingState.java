package com.lpnu.PZ.domain.pizza.state;

import com.lpnu.PZ.domain.Pizza;

public class BakingState extends PizzaState {

    public BakingState(final Pizza pizza) {
        super(pizza);
        this.cookOperation = CookOperation.BAKING;
    }

    @Override
    public void moveNextState() {
        this.pizza.changeState(new DoneState(this.pizza));
    }

    @Override
    public double getCompletion() {
        return this.cookOperation.getCompletionPercent();
    }
}
