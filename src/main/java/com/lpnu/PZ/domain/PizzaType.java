package com.lpnu.PZ.domain;

import java.util.Arrays;

public enum PizzaType {
    MARGHERITA(15),
    PEPPERONI(20),
    VEGGIE(18),
    SUPREME(25),
    BBQ_CHICKEN(22),
    HAWAIIAN(18),
    MEXICAN(23),
    MUSHROOM_OLIVE(20),
    FOUR_CHEESE(17),
    SPINACH_ARTICHOKE(21);

    private final int minutesToCreate;

    PizzaType(int minutesToCreate) {
        this.minutesToCreate = minutesToCreate;
    }

    public int getMinutesToCreate() {
        return minutesToCreate;
    }
}
