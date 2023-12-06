package com.lpnu.PZ.domain.pizza.state;

import com.lpnu.PZ.domain.Pizza;

public class MakingDoughState extends PizzaState {

    public MakingDoughState(final Pizza pizza) {
        super(pizza);
    }

    @Override
    public void moveNextState() {
        this.pizza.changeState(new PreparingToppingsState(this.pizza));
    }

    @Override
    public double getCompletion() {
        return 0.3;
    }
}
