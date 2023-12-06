package com.lpnu.PZ.domain.pizza.state;

import com.lpnu.PZ.domain.Pizza;

public class AssemblingState extends PizzaState {

    public AssemblingState(final Pizza pizza) {
        super(pizza);
    }

    @Override
    public void moveNextState() {
        this.pizza.changeState(new MakingDoughState(this.pizza));
    }

    @Override
    public double getCompletion() {
        return 0.1;
    }
}
