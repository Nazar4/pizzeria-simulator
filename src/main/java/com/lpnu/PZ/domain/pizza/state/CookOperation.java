package com.lpnu.PZ.domain.pizza.state;

import lombok.Getter;

@Getter
public enum CookOperation {
    ASSEMBLING(0.1),
    MAKING_DOUGH(0.3),
    PREPARE_TOPPINGS(0.2),
    BAKING(0.4),
    DONE(0);

    private final double completionPercent;

    CookOperation(final double completionPercent) {
        this.completionPercent = completionPercent;
    }
}
