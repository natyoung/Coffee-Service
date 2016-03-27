package org.coffee.service;

import com.google.gson.Gson;
import org.coffee.data.DataStore;
import org.coffee.domain.Coffee;
import org.coffee.domain.Menu;
import org.wso2.msf4j.MicroservicesRunner;
import java.net.URISyntaxException;
import java.util.*;

public class Application {
    private static final Gson GSON = new Gson();

    public static void main(String[] args) throws URISyntaxException {
        final DataStore dataStore = new DataStore();
        dataStore.set("coffees", GSON.toJson(coffeeList(), ArrayList.class));
        final Menu menu = new Menu(dataStore);
        new MicroservicesRunner(4567)
                .deploy(new CoffeeService(menu))
                .start();
    }

    private static List<Coffee> coffeeList() {
        return new ArrayList<Coffee>() {{
                    add(new Coffee("long black", "/order/long-black", 3, 8, 0));
                    add(new Coffee("flat white", "/order/flat-white", 3.5, 5, 2));
                    add(new Coffee("latte", "/order/latte", 3.5, 5, 3));
                    add(new Coffee("espresso", "/order/espresso", 2, 10, 0));
                    add(new Coffee("machiato", "/order/machiato", 2.5, 10, 0.5));
                }};
    }
}
