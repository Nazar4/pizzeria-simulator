package com.lpnu.PZ.services;

import com.lpnu.PZ.domain.Client;
import com.lpnu.PZ.domain.Order;
import lombok.Getter;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

@Getter
public class Paydesk {
    Queue<Order> ordinaryQueue;
    Queue<Order> priorityQueue;
    List<Client> clients;

    public Paydesk() {
        this.clients = new ArrayList<>();
        this.ordinaryQueue = new LinkedList<>();
        this.priorityQueue = new PriorityQueue<>();
    }
}
