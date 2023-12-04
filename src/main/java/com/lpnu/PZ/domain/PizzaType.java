package com.lpnu.PZ.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;

public enum PizzaType {
    MARGHERITA(15, 250),
    PEPPERONI(20, 200),
    VEGGIE(18, 175),
    SUPREME(25, 190),
    BBQ_CHICKEN(22, 230),
    HAWAIIAN(18, 220),
    MEXICAN(23, 210),
    MUSHROOM_OLIVE(20, 270),
    FOUR_CHEESE(17, 255),
    SPINACH_ARTICHOKE(21, 150);

    @Getter
    @Setter
    private int minutesToCreate;
    @Getter
    private final double price;

    PizzaType(int minutesToCreate, double price) {
        this.minutesToCreate = minutesToCreate;
        this.price = price;
    }
}
