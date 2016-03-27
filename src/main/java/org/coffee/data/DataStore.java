package org.coffee.data;

import com.google.gson.Gson;
import org.coffee.domain.beans.Coffee;
import org.coffee.service.Application;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Protocol;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class DataStore {
    private static final Gson GSON = new Gson();
    private JedisPool pool;

    private JedisPool getJedisPool() {
        if(this.pool == null) {
            try {
                URI redisURI = new URI(System.getenv("REDISTOGO_URL"));
                this.pool = new JedisPool(new JedisPoolConfig(),
                        redisURI.getHost(),
                        redisURI.getPort(),
                        Protocol.DEFAULT_TIMEOUT,
                        null);
            }
            catch(URISyntaxException e) {
                throw new RuntimeException();
            }
        }
        return this.pool;
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
        new ArrayList<Coffee>() {{
            add(new Coffee("long black", "/createOrder/long-black", 3, 8, 0));
            add(new Coffee("flat white", "/createOrder/flat-white", 3.5, 5, 2));
            add(new Coffee("latte", "/createOrder/latte", 3.5, 5, 3));
            add(new Coffee("espresso", "/createOrder/espresso", 2, 10, 0));
            add(new Coffee("machiato", "/createOrder/machiato", 2.5, 10, 0.5));
        }}.stream().forEach(c -> addToList(Application.KEY_COFFEES, GSON.toJson(c, Coffee.class)));
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
