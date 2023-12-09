package com.lpnu.PZ.domain.pizza.state;

import com.lpnu.PZ.domain.Pizza;

public class DoneState extends PizzaState {

    public DoneState(final Pizza pizza) {
        super(pizza);
        this.cookOperation = CookOperation.DONE;
    }

    @Override
    public void moveNextState() {
        this.pizza.getLog().logEnd();
        this.pizza.setPrepared(true);
    }

    @Override
    public double getCompletion() {
        return this.cookOperation.getCompletionPercent();
    }
}
