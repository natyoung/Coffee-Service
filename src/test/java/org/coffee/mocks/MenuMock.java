package org.coffee.mocks;

import org.coffee.domain.Coffee;
import org.coffee.domain.Menu;

import java.util.ArrayList;
import java.util.List;

public class MenuMock extends Menu {
    public MenuMock() {
        super(new DataStoreMock());
    }

    public List<Coffee> getCoffees() {
        return new ArrayList<Coffee>() {{
            add(new Coffee("long black", "/order/long-black", 3, 8, 0));
            add(new Coffee("flat white", "/order/flat-white", 3.5, 5, 2));
            add(new Coffee("latte", "/order/latte", 3.5, 5, 3));
            add(new Coffee("espresso", "/order/espresso", 2, 10, 0));
            add(new Coffee("machiato", "/order/machiato", 2.5, 10, 0.5));
        }};
    }
}
