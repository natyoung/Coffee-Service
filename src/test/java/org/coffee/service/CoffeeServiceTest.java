package org.coffee.service;

import com.google.common.base.Charsets;
import com.google.common.io.ByteStreams;
import com.google.gson.Gson;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpResponseStatus;
import org.coffee.mocks.MenuMock;
import org.junit.*;
import org.wso2.msf4j.MicroservicesRunner;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import org.coffee.domain.Menu;

public class CoffeeServiceTest {
    private static URI baseURI;
    private static final Gson GSON = new Gson();
    private static final Menu menu = new MenuMock();
    private static final MicroservicesRunner microservicesRunner = new MicroservicesRunner(4568);

    @BeforeClass
    public static void setup() throws Exception {
        baseURI = URI.create(String.format("http://%s:%d", "localhost", 4568));
        microservicesRunner
                .deploy(new CoffeeService(menu))
                .start();
    }

    @Test
    public void testGetMenuReturnsHttpResponseStatusOK() throws Exception {
        URL url = baseURI.resolve("/menu").toURL();
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        Assert.assertEquals(HttpResponseStatus.OK.code(), urlConnection.getResponseCode());
    }

    @Test
    public void testGetMenuReturnsListOfCoffees() throws Exception {
        URL url = baseURI.resolve("/menu").toURL();
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        String response = getResponse(urlConnection);
        Assert.assertTrue(response.equals(GSON.toJson(menu.getCoffees())));
    }

    @Test
    public void testThatCoffeeIsReady() throws Exception {
        URL url = baseURI.resolve("/order/123").toURL();
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        String response = getResponse(urlConnection);
        Assert.assertEquals(response, "\"READY\"");
    }

    @Test
    public void testOrderCoffeeReturnsHttpResponseStatusCreated() throws Exception {
        URL url = baseURI.resolve("/order/long_black").toURL();
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setDoOutput(true);
        urlConnection.setRequestMethod(HttpMethod.POST.name());
        Assert.assertEquals(HttpResponseStatus.CREATED.code(), urlConnection.getResponseCode());
    }

    @Test
    public void testOrderCoffeeReturns() throws Exception {
        URL url = baseURI.resolve("/order/long_black").toURL();
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setDoOutput(true);
        urlConnection.setRequestMethod(HttpMethod.POST.name());
        String response = getResponse(urlConnection);
        Assert.assertEquals("\"{\\\"order\\\":\\\"/order/123\\\",\\\"wait_time\\\":\\\"5\\\"}\"", response);
    }

    @AfterClass
    public static void tearDown() throws Exception {
        microservicesRunner.stop();
    }

    protected String getResponse(HttpURLConnection urlConnection) throws IOException {
        return new String(ByteStreams.toByteArray(urlConnection.getInputStream()), Charsets.UTF_8);
    }
}
