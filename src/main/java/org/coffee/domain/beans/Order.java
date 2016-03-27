package org.coffee.domain.beans;

import java.util.List;

public class Order {
    private final String coffeeName;
    private final String size;
    private final List<String> extras;

    public Order(String coffeeName, String size, List<String> extras) {
        this.coffeeName = coffeeName;
        this.size = size;
        this.extras = extras;
    }
}
