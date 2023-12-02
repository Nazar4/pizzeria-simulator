package com.lpnu.PZ.domain;

import lombok.Getter;

public class Cook extends Thread {
    private static int cookCounter = 0;

    @Getter
    private final int cookId;
    @Getter
    private CookState cookState;
    private volatile boolean stopped;

    public Cook() {
        this.cookId = generateCookId();
        this.cookState = CookState.COOKING;
        this.stopped = false;
    }

    public void setCookState(CookState cookState) {
        this.cookState = cookState;
    }

    @Override
    public void run() {
        while (!stopped) {
            // here will be the logic of cook doing his work
        }
        System.out.println("Cook " + cookId + " has stopped.");
    }

    public void stopCook() {
        stopped = true;
    }

    private synchronized int generateCookId() {
        return ++cookCounter;
    }
}

