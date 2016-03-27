package org.coffee.domain;

import com.google.gson.Gson;
import org.coffee.data.DataStore;

import java.util.ArrayList;
import java.util.List;

public class Menu {
    private final DataStore dataStore;

    private static final Gson GSON = new Gson();

    public Menu(DataStore dataStore) {
        this.dataStore = dataStore;
    }

    public List<Coffee> getCoffees() {
        String coffees = this.dataStore.get("coffees");
        return GSON.fromJson(coffees, ArrayList.class);
    }
}
