package org.coffee.service;

import model.Coffee;
import org.wso2.msf4j.MicroservicesRunner;

import java.util.*;

public class Application {
    public static void main(String[] args) {
        final List<Coffee> coffees = Collections.unmodifiableList(
                new ArrayList<Coffee>() {{
                    add(new Coffee("long black", "/order/long-black", 3, 8, 0));
                    add(new Coffee("flat white", "/order/flat-white", 3.5, 5, 2));
                    add(new Coffee("latte", "/order/latte", 3.5, 5, 3));
                    add(new Coffee("espresso", "/order/espresso", 2, 10, 0));
                    add(new Coffee("machiato", "/order/machiato", 2.5, 10, 0.5));
                }});
        final Map<String, List<Coffee>> menu = Collections.unmodifiableMap(
                new HashMap<String, List<Coffee>>() {{
                    put("coffees", coffees);
                }});
        new MicroservicesRunner(4567)
                .deploy(new CoffeeService(menu))
                .start();
    }
}
