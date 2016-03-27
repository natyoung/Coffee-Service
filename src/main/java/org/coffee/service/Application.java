package org.coffee.service;

import org.coffee.data.DataStore;
import org.coffee.domain.Menu;
import org.wso2.msf4j.MicroservicesRunner;
import java.net.URISyntaxException;

public class Application {
    public static final int PORT = 4567;
    public static final String KEY_COFFEES = "coffees";
    public static final String KEY_ORDERS = "orders";

    public static void main(String[] args) throws URISyntaxException {
        final DataStore dataStore = new DataStore();
        dataStore.setUp();
        final Menu menu = new Menu(dataStore);
        new MicroservicesRunner(PORT)
                .deploy(new CoffeeService(menu))
                .start();
    }
}
