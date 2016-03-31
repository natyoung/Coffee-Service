package org.coffee.mocks;

import com.google.gson.Gson;
import org.coffee.data.DataStore;
import org.coffee.domain.beans.Coffee;
import org.coffee.service.Application;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DataStoreMock extends DataStore {
    private static final Gson GSON = new Gson();

    public long addToList(String key, String... values) { return 321; }
    public List<String> getAllInList(String key) {
        return new ArrayList<Coffee>() {{
            add(new Coffee("long black", "/createOrder/long-black", 3, 8, 0));
            add(new Coffee("flat white", "/createOrder/flat-white", 3.5, 5, 2));
            add(new Coffee("latte", "/createOrder/latte", 3.5, 5, 3));
            add(new Coffee("espresso", "/createOrder/espresso", 2, 10, 0));
            add(new Coffee("machiato", "/createOrder/machiato", 2.5, 10, 0.5));
        }}.stream()
                .map(c -> GSON.toJson(c, Coffee.class))
                .collect(Collectors.toCollection(ArrayList::new));
    }
    public String set(String key, String value) { return "OK"; };
    public String get(String key) {
        return key.equals("321") ? "READY" : null;
    };
    public void setUp() {}
}
