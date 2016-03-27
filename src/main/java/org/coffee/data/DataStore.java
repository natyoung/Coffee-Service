package org.coffee.data;

import com.google.gson.Gson;
import org.coffee.domain.beans.Coffee;
import org.coffee.domain.beans.Order;
import org.coffee.service.Application;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Protocol;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

public class DataStore {
    private static final Gson GSON = new Gson();
    private JedisPool pool;

    private JedisPool getJedisPool() {
        if(this.pool == null) {
            try {
                URI redisURI = new URI(System.getenv("REDISTOGO_URL"));
                String password = redisURI.getUserInfo().equals(":") ? null : redisURI.getUserInfo().split(":",2)[1];
                System.out.println(password);
                this.pool = new JedisPool(new JedisPoolConfig(),
                        redisURI.getHost(),
                        redisURI.getPort(),
                        Protocol.DEFAULT_TIMEOUT,
                        password);
            }
            catch(URISyntaxException e) {
                throw new RuntimeException();
            }
        }
        return this.pool;
    }

    public String set(String key, String value) {
        Jedis jedis = getJedisPool().getResource();
        try {
            return jedis.set(key, value);
        }
        finally {
            jedis.close();
        }
    }

    public String get(String key) {
        Jedis jedis = getJedisPool().getResource();
        try {
            return jedis.get(key);
        }
        finally {
            jedis.close();
        }
    }

    public long addToList(String key, String... values) {
        Jedis jedis = getJedisPool().getResource();
        try {
            return jedis.lpush(key, values);
        }
        finally {
            jedis.close();
        }
    }

    public List<String> getAllInList(String key) {
        Jedis jedis = getJedisPool().getResource();
        try {
            return jedis.lrange(key, 0, -1);
        }
        finally {
            jedis.close();
        }
    }

    public void setUp() {
        clean();
        insertCoffees();
        insertOrders();
        insertOrderStatuses();
    }

    private void insertCoffees() {
        new ArrayList<Coffee>() {{
            add(new Coffee("long black", "/createOrder/long-black", 3, 8, 0));
            add(new Coffee("flat white", "/createOrder/flat-white", 3.5, 5, 2));
            add(new Coffee("latte", "/createOrder/latte", 3.5, 5, 3));
            add(new Coffee("espresso", "/createOrder/espresso", 2, 10, 0));
            add(new Coffee("machiato", "/createOrder/machiato", 2.5, 10, 0.5));
        }}.stream().forEach(c -> addToList(Application.KEY_COFFEES, GSON.toJson(c, Coffee.class)));
    }

    private void insertOrders() {
        final List<String> extras = Collections.unmodifiableList(new ArrayList<String>() {{
            add("skim-milk");
            add("sugar");
        }});
        Collections.nCopies(123, new Order("long black", "small", extras))
                .stream()
                .forEach(o -> addToList(Application.KEY_ORDERS, GSON.toJson(o, Order.class)));
    }

    private void insertOrderStatuses() {
        IntStream.range(1, 124).forEach((i) -> set(String.valueOf(i), "READY"));
    }

    private void clean() {
        Jedis jedis = getJedisPool().getResource();
        try {
            jedis.del(Application.KEY_ORDERS);
            jedis.del(Application.KEY_COFFEES);
        }
        finally {
            jedis.close();
        }
    }
}
