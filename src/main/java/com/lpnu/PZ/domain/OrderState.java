package com.lpnu.PZ.domain;

public enum OrderState {
    //maybe for order we will only need ORDER_ACCEPTED, PREPARING_ORDER, ORDER_FINISHED
    ORDER_ACCEPTED,
    MAKING_DOUGH,
    PREPARING_TOPPINGS,
    BAKING,
    DONE
}
