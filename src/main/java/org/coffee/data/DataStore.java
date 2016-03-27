package org.coffee.data;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Protocol;

import java.net.URI;
import java.net.URISyntaxException;

public class DataStore {
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
}
