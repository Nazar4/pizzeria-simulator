package com.lpnu.PZ.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.Queue;

@Getter
@Setter
public class Client {
    private String clientName;
    private Order order;
//    private Queue queue;

    public Client(String clientName, Order order) {
        this.clientName = clientName;
        this.order = order;
    }

}
