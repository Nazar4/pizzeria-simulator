package com.lpnu.PZ.domain;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PizzaLog {
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    public void logStart() {
        startDate = LocalDateTime.now();
    }

    public void logEnd() {
        endDate = LocalDateTime.now();
    }
}
