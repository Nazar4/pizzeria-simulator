package com.lpnu.PZ.domain;

import lombok.Getter;

@Getter
public enum CookState {
    //maybe for cook state we will have everything we have in order state now
    COOKING("COOKING"),
    ON_BREAK("ON BREAK");

    private final String displayValue;

    CookState(String displayValue) {
        this.displayValue = displayValue;
    }

}
