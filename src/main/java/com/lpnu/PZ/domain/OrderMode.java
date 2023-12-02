package com.lpnu.PZ.domain;

import lombok.Getter;

@Getter
public enum OrderMode {
    PARTIAL_PROCESSING("Partial Processing"),
    FULL_PROCESSING("Full Processing");

    private final String displayValue;

    OrderMode(String displayValue) {
        this.displayValue = displayValue;
    }

}
