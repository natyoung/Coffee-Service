package org.coffee.domain;

import org.coffee.data.DataStore;
import org.coffee.domain.beans.Order;
import org.coffee.mocks.DataStoreMock;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class MenuTest {
    private static Menu menu;
    private static final DataStore dataStore = new DataStoreMock();

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
        String orderResult = menu.createOrder(order);
        Assert.assertEquals("{\"order\":321,\"wait_time\":5}", orderResult);
    }

    @Test
    public void testOrderStatusIsReady() throws Exception {
        String orderStatus = menu.getOrderStatus(321);
        Assert.assertTrue(orderStatus.equals("READY"));
    }
}
