package com.lpnu.PZ.domain;

import lombok.Getter;

@Getter
public class Client {
    private final String clientName;
    private final Order order;

    public Client(final String clientName, final Order order) {
        this.clientName = clientName;
        this.order = order;
    }

}
