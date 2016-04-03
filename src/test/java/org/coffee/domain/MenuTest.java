package org.coffee.domain;

import com.google.gson.Gson;
import org.coffee.data.DataStore;
import org.coffee.domain.beans.Coffee;
import org.coffee.domain.beans.Order;
import org.coffee.domain.beans.OrderResponse;
import org.coffee.mocks.DataStoreMock;
import org.coffee.service.Application;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MenuTest {
    private static Menu menu;
    private static final DataStore dataStore = new DataStoreMock();
    private static final Gson GSON = new Gson();

    @BeforeClass
    public static void setUp() throws Exception {
        menu = new Menu(dataStore);
    }

    @Test
    public void testCreateOrderReturnsOrderDetails() throws Exception {
        List<String> extras = new ArrayList<String>() {{
            add("dirt");
            add("flour");
        }};
        Order order = new Order("long black", "large", extras);
        OrderResponse orderResponse = menu.createOrder(order);
        OrderResponse expected = new OrderResponse("321", 5);
        Assert.assertEquals(expected, orderResponse);
    }

    @Test
    public void testOrderStatusIsReady() throws Exception {
        HashMap<String, String> orderStatus = menu.getOrderStatus(321);
        Assert.assertTrue(orderStatus.get("status").equals("READY"));
    }

    @Test
    public void testMenuReturnsCoffeeList() throws Exception {
        HashMap<String, List<Coffee>> coffees = menu.getCoffees();
        Assert.assertTrue(coffees.get(Application.KEY_COFFEES).size() > 0);
    }
}
