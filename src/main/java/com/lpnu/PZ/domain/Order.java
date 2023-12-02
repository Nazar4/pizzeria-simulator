package com.lpnu.PZ.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Order implements Comparable<Order> {
    @Getter
    @Setter
    private List<Pizza> pizzas;
    private int priority;
    private double totalPrice;
    @Getter
    @Setter
    private OrderState orderState;
    @Getter
    private OrderMode orderMode;

    public Order(List<Pizza> pizzas, boolean withPriority) {
        this.pizzas = pizzas;
        this.orderState = OrderState.ORDER_ACCEPTED;
        if (withPriority) {
            this.priority = ThreadLocalRandom.current().nextInt(0, 11);
        }
        totalPrice = pizzas.stream().mapToDouble(Pizza::getPrice).sum();
    }

    @Override
    public int compareTo(final Order other) {
        return Integer.compare(this.priority, other.priority);
    }
}
