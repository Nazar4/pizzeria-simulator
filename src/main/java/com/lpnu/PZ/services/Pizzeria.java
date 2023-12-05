package com.lpnu.PZ.services;

public class Pizzeria {
    private Kitchen kitchen;
    private Paydesk paydesk;
    private ClientGenerationStrategy clientGenerationStrategy;

    public Pizzeria(final Kitchen kitchen, final Paydesk paydesk,
                    final ClientGenerationStrategy clientGenerationStrategy) {
        this.kitchen = kitchen;
        this.paydesk = paydesk;
        this.clientGenerationStrategy = clientGenerationStrategy;
    }

    public void runPizzeria() {

    }
}
