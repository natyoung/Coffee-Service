package org.coffee.service;

import io.netty.handler.codec.http.HttpResponseStatus;
import org.junit.*;
import org.wso2.msf4j.MicroservicesRunner;

import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

public class MenuServiceTest {
    protected static URI baseURI;

    private static final MicroservicesRunner microservicesRunner = new MicroservicesRunner(4568);

    @BeforeClass
    public static void setup() throws Exception {
        baseURI = URI.create(String.format("http://%s:%d", "localhost", 4568));
        microservicesRunner
                .deploy(new MenuService())
                .start();
    }

    @Test
    public void testGetMenuReturnsOK() throws Exception {
        URL url = baseURI.resolve("/menu").toURL();
        HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
        Assert.assertEquals(HttpResponseStatus.OK.code(), urlConn.getResponseCode());
    }

    @After
    public void tearDown() throws Exception {
        microservicesRunner.stop();
    }
}
