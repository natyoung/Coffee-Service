package org.coffee.domain;

import com.google.gson.Gson;
import org.coffee.data.DataStore;
import org.coffee.domain.beans.Order;
import org.coffee.domain.beans.OrderResponse;
import org.coffee.service.Application;

import java.util.HashMap;
import java.util.List;

public class Menu {
    private final DataStore dataStore;

    private static final Gson GSON = new Gson();

    public Menu(DataStore dataStore) {
        this.dataStore = dataStore;
    }

    public HashMap<String, List<String>> getCoffees() {
        HashMap<String, List<String>> map = new HashMap<>();
        map.put(Application.KEY_COFFEES, this.dataStore.getAllInList(Application.KEY_COFFEES));
        return map;
    }

    public String createOrder(Order order) {
        long orderId = this.dataStore.addToList(Application.KEY_ORDERS, GSON.toJson(order, Order.class));
        this.dataStore.set(String.valueOf(orderId), "READY");
        OrderResponse response = new OrderResponse(orderId, 5);
        return GSON.toJson(response, OrderResponse.class);
    }

    public String getOrderStatus(long orderId) {
        return this.dataStore.get(String.valueOf(orderId));
    }
}
