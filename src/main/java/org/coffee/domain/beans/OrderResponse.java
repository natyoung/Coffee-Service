package org.coffee.domain.beans;

public class OrderResponse {
    private long order;
    private long wait_time;

    public OrderResponse(long order, long wait_time) {
        this.order = order;
        this.wait_time = wait_time;
    }
}
