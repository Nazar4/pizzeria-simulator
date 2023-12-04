package com.lpnu.PZ.domain;

import lombok.Getter;

@Getter
public enum CookState {
    COOKING("COOKING"),
    ON_BREAK("ON BREAK");

    private final String displayValue;

    CookState(String displayValue) {
        this.displayValue = displayValue;
    }

}
