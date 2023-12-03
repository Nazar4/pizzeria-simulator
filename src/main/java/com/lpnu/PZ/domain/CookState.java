package com.lpnu.PZ.domain;

import lombok.Getter;

@Getter
public enum CookState {
    ASSEMBLING("Assembling"),
    MAKING_DOUGH("Making dough"),
    PREPARING_TOPPINGS("Preparing toppings"),
    BAKING("Baking"),
    DONE("Done");
//    COOKING("COOKING"),
//    ON_BREAK("ON BREAK");

    private final String displayValue;

    CookState(String displayValue) {
        this.displayValue = displayValue;
    }

}
