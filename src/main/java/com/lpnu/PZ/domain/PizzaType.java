package com.lpnu.PZ.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;

public enum PizzaType {
    MARGHERITA(1),
    PEPPERONI(1.5),
    VEGGIE(1.25),
    SUPREME(1.75),
    BBQ_CHICKEN(1.8),
    HAWAIIAN(1.8),
    MEXICAN(1.8),
    MUSHROOM_OLIVE(1.5),
    FOUR_CHEESE(1.25),
    SPINACH_ARTICHOKE(1.75);

    @Getter
    private final double timeComplexity;

    PizzaType(double timeComplexity) {
        this.timeComplexity = timeComplexity;
    }
}
