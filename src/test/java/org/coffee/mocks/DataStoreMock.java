package org.coffee.mocks;

import org.coffee.data.DataStore;

public class DataStoreMock extends DataStore {
    public String get(String key) { return ""; }
    public String set(String key, String value) { return ""; }
}
