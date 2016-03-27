package org.coffee.domain;

import com.google.gson.Gson;
import org.coffee.data.DataStore;
import org.coffee.domain.beans.Order;
import org.coffee.domain.beans.OrderResponse;
import org.coffee.service.Application;

import java.util.List;

public class Menu {
    private final DataStore dataStore;

    private static final Gson GSON = new Gson();

    public Menu(DataStore dataStore) {
        this.dataStore = dataStore;
    }

    public List<String> getCoffees() {
        return this.dataStore.getAllInList(Application.KEY_COFFEES);
    }

    public String createOrder(Order order) {
        long orderId = this.dataStore.addToList(Application.KEY_ORDERS, GSON.toJson(order, Order.class));
        OrderResponse response = new OrderResponse(orderId, 5);
        return GSON.toJson(response, OrderResponse.class);
    }

    public String getOrderStatus(long orderId) {
        return "READY";
    }
}
