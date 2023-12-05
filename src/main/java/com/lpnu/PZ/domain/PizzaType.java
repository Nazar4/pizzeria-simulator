package com.lpnu.PZ.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;

public enum PizzaType {
    MARGHERITA(1, 250),
    PEPPERONI(1.5, 200),
    VEGGIE(1.25, 175),
    SUPREME(1.75, 190),
    BBQ_CHICKEN(1.5, 230),
    HAWAIIAN(1.2, 220),
    MEXICAN(1.8, 210),
    MUSHROOM_OLIVE(1.5, 270),
    FOUR_CHEESE(1.25, 255),
    SPINACH_ARTICHOKE(1.6, 150);

    @Getter
    private final double timeComplexity;
    @Getter
    private final double price;

    PizzaType(double timeComplexity, double price) {
        this.timeComplexity = timeComplexity;
        this.price = price;
    }
}
