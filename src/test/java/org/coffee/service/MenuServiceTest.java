package org.coffee.service;

import com.google.common.base.Charsets;
import com.google.common.io.ByteStreams;
import com.google.gson.Gson;
import io.netty.handler.codec.http.HttpResponseStatus;
import model.Coffee;
import org.junit.*;
import org.wso2.msf4j.MicroservicesRunner;

import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.*;

public class MenuServiceTest {
    private static URI baseURI;
    private static final Gson GSON = new Gson();
    private static final Map<String, List<Coffee>> menu = Collections.unmodifiableMap(
            new HashMap<String, List<Coffee>>() {{
                put("coffees", Collections.unmodifiableList(
                        new ArrayList<Coffee>() {{
                            add(new Coffee("long black", "/order/long-black", 3, 8, 0));
                            add(new Coffee("flat white", "/order/flat-white", 3.5, 5, 2));
                            add(new Coffee("latte", "/order/latte", 3.5, 5, 3));
                            add(new Coffee("espresso", "/order/espresso", 2, 10, 0));
                            add(new Coffee("machiato", "/order/machiato", 2.5, 10, 0.5));
                        }}));
            }});

    private static final MicroservicesRunner microservicesRunner = new MicroservicesRunner(4568);

    @BeforeClass
    public static void setup() throws Exception {
        baseURI = URI.create(String.format("http://%s:%d", "localhost", 4568));
        microservicesRunner
                .deploy(new MenuService(menu))
                .start();
    }

    @Test
    public void testGetMenuReturnsOK() throws Exception {
        URL url = baseURI.resolve("/menu").toURL();
        HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
        Assert.assertEquals(HttpResponseStatus.OK.code(), urlConn.getResponseCode());
    }

    @Test
    public void testGetMenuReturnsListOfCoffees() throws Exception {
        URL url = baseURI.resolve("/menu").toURL();
        HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
        String json = new String(ByteStreams.toByteArray(urlConn.getInputStream()), Charsets.UTF_8);
        Assert.assertTrue(json.equals(GSON.toJson(menu)));
    }

    @AfterClass
    public static void tearDown() throws Exception {
        microservicesRunner.stop();
    }
}
