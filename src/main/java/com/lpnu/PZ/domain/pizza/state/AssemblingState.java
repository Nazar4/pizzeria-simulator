package com.lpnu.PZ.domain.pizza.state;

import com.lpnu.PZ.domain.Pizza;

public class AssemblingState extends PizzaState {

    public AssemblingState(final Pizza pizza) {
        super(pizza);
        this.cookOperation = CookOperation.ASSEMBLING;
        this.pizza.getLog().logStart();
    }

    @Override
    public void moveNextState() {
        this.pizza.changeState(new MakingDoughState(this.pizza));
    }

    @Override
    public double getCompletion() {
        return this.cookOperation.getCompletionPercent();
    }
}
