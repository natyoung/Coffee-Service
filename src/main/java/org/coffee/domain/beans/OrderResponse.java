package org.coffee.domain.beans;

public class OrderResponse {
    private final long order;
    private final long wait_time;

    public OrderResponse(long order, long wait_time) {
        this.order = order;
        this.wait_time = wait_time;
    }
}
