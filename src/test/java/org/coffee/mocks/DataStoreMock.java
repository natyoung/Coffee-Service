package org.coffee.mocks;

import org.coffee.data.DataStore;

import java.util.ArrayList;
import java.util.List;

public class DataStoreMock extends DataStore {
    public long addToList(String key, String... values) { return 321; }
    public List<String> getAllInList(String key) { return new ArrayList<>(); }
    public String set(String key, String value) { return "OK"; };
    public String get(String key) {
        return key.equals("321") ? "READY" : null;
    };
    public void setUp() {}
}
