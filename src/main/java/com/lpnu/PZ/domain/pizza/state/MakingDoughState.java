package com.lpnu.PZ.domain.pizza.state;

import com.lpnu.PZ.domain.Pizza;

public class MakingDoughState extends PizzaState {

    public MakingDoughState(final Pizza pizza) {
        super(pizza);
        this.cookOperation = CookOperation.MAKING_DOUGH;
    }

    @Override
    public void moveNextState() {
        this.pizza.changeState(new PreparingToppingsState(this.pizza));
    }

    @Override
    public double getCompletion() {
        return this.cookOperation.getCompletionPercent();
    }
}
