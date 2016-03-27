package org.coffee.mocks;

import org.coffee.data.DataStore;

import java.util.ArrayList;
import java.util.List;

public class DataStoreMock extends DataStore {
    public long addToList(String key, String... values) { return 321; }
    public List<String> getAllInList(String key) { return new ArrayList<>(); }
}
