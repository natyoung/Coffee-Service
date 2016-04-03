package org.coffee.domain.beans;

public class OrderResponse {
    private final String order;
    private final long wait_time;

    public OrderResponse(String order, long wait_time) {
        this.order = order;
        this.wait_time = wait_time;
    }

    public boolean equals(final Object other) {
        if (this == other)
            return true;
        if (!(other instanceof OrderResponse))
            return false;
        return ((OrderResponse) other).order.equals(this.order) &&
                ((OrderResponse) other).wait_time == this.wait_time;
    }
}
