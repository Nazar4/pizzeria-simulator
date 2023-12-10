package com.lpnu.PZ.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Getter
public class Order implements Comparable<Order> {
    private final List<Pizza> pizzas;
    private final double totalPrice;
    @Setter
    private OrderState orderState;
    private final OrderMode orderMode;
    private int priority;

    public Order(final List<Pizza> pizzas, final boolean withPriority, final boolean partialProcessing) {
        this.pizzas = pizzas;
        this.orderState = OrderState.ORDER_ACCEPTED;
        this.orderMode = partialProcessing ? OrderMode.PARTIAL_PROCESSING : OrderMode.FULL_PROCESSING;
        if (withPriority) {
            this.priority = ThreadLocalRandom.current().nextInt(1, 11);
        }
        totalPrice = pizzas.stream().mapToDouble(pizza -> pizza.getPizzaType().getPrice()).sum();
    }

    @Override
    public int compareTo(final Order other) {
        return Integer.compare(this.priority, other.priority);
    }

    @Override
    public String toString() {
        StringBuilder orderString = new StringBuilder("Order {");
        orderString.append("state:").append(orderState)
                .append(", orderMode:").append(orderMode)
                .append(", totalPrice:").append(totalPrice)
                .append(", priority:").append(priority)
                .append(", pizzas:\n");

        for (final Pizza pizza : pizzas) {
            orderString.append("  ").append(pizza.toString()).append("\n");
        }

        orderString.append("}");

        return orderString.toString();
    }
}
