package org.coffee.domain.beans;

public class Coffee {
    private final String name;
    private final String order_path;
    private final double price;
    private final int caffeine_level;
    private final double milk_ratio;

    public Coffee(String name, String order_path, double price, int caffeine_level, double milk_level) {
        this.name = name;
        this.order_path = order_path;
        this.price = price;
        this.caffeine_level = caffeine_level;
        this.milk_ratio = milk_level;
    }
}
