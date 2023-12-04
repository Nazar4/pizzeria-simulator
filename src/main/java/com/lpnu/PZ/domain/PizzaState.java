package com.lpnu.PZ.domain;

public enum PizzaState {
    ASSEMBLING("Assembling"),
    MAKING_DOUGH("Making dough"),
    PREPARING_TOPPINGS("Preparing toppings"),
    BAKING("Baking"),
    DONE("Done");

    private final String displayValue;

    PizzaState(String displayValue) {
        this.displayValue = displayValue;
    }
}
