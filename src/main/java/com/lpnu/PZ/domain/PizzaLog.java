package com.lpnu.PZ.domain;

import lombok.Getter;

import java.time.LocalDateTime;

public class PizzaLog {
    @Getter
    private LocalDateTime startDate;

    @Getter
    private LocalDateTime endDate;

    public void logStart() {
        startDate = LocalDateTime.now();
    }

    public void logEnd() {
        endDate = LocalDateTime.now();
    }
}
