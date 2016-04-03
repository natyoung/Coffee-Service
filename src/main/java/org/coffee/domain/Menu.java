package org.coffee.domain;

import com.google.gson.Gson;
import org.coffee.data.DataStore;
import org.coffee.domain.beans.Coffee;
import org.coffee.domain.beans.Order;
import org.coffee.domain.beans.OrderResponse;
import org.coffee.service.Application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class Menu {
    private final DataStore dataStore;

    private static final Gson GSON = new Gson();

    public Menu(DataStore dataStore) {
        this.dataStore = dataStore;
    }

    public HashMap<String, List<Coffee>> getCoffees() {
        List<Coffee> coffees = this.dataStore.getAllInList(Application.KEY_COFFEES)
            .stream()
            .map(c -> GSON.fromJson(c, Coffee.class))
            .collect(Collectors.toCollection(ArrayList::new));
        HashMap<String, List<Coffee>> map = new HashMap<>();
        map.put(Application.KEY_COFFEES, coffees);;
        return map;
    }

    public OrderResponse createOrder(Order order) {
        long orderId = this.dataStore.addToList(Application.KEY_ORDERS, GSON.toJson(order, Order.class));
        this.dataStore.set(String.valueOf(orderId), Application.COFFEE_STATUS);
        return new OrderResponse(String.valueOf(orderId), 5);
    }

    public HashMap<String, String> getOrderStatus(long orderId) {
        String order = this.dataStore.get(String.valueOf(orderId));
        HashMap<String, String> status = new HashMap<>();
        status.put(Application.KEY_ORDER_STATUS, order);
        return status;
    }
}
