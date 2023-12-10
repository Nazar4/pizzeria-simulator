package com.lpnu.PZ.dto;

import com.lpnu.PZ.domain.Order;
import com.lpnu.PZ.domain.OrderMode;
import com.lpnu.PZ.domain.OrderState;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderDTO {
    private List<PizzaDTO> pizzas;
    private double totalPrice;
    private OrderState orderState;
    private OrderMode orderMode;

    public static OrderDTO mapToOrderDTO(final Order order) {
        final OrderDTO orderDTO = new OrderDTO();
        orderDTO.setTotalPrice(order.getTotalPrice());
        orderDTO.setOrderState(order.getOrderState());
        orderDTO.setOrderMode(order.getOrderMode());
        orderDTO.setPizzas(order.getPizzas().stream().map(PizzaDTO::mapToPizzaDTO).toList());
        return orderDTO;
    }
}
