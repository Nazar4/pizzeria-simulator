package com.lpnu.PZ.services;

import com.lpnu.PZ.dto.PizzeriaConfigurationDTO;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class Pizzeria {
    private Kitchen kitchen;
    private Paydesk paydesk;
    private ClientGenerationStrategy clientGenerationStrategy;

    public void configurePizzeria(final PizzeriaConfigurationDTO configuration) {
        this.kitchen = Kitchen.getInstance(configuration.getCooksNumber());
        this.clientGenerationStrategy = configuration.isRandomGenerationStrategy()
                ? new RandomGenerationStrategy(configuration.getPizzasNumber()) : new IntervalGenerationStrategy(configuration.getPizzasNumber());
        this.paydesk = new Paydesk();
        runPizzeria();
    }

    public void runPizzeria() {
        while (true) {
            //todo to be implemented
        }
    }

    public boolean stopCookById(String cookId) {
        return this.kitchen.stopCookById(cookId);
    }

    public boolean resumeCookById(String cookId) {
        return this.kitchen.resumeCookById(cookId);
    }
}
