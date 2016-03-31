package org.coffee.service;

import com.google.common.base.Charsets;
import com.google.common.io.ByteStreams;
import com.google.gson.Gson;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpResponseStatus;
import org.coffee.domain.Menu;
import org.coffee.mocks.DataStoreMock;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.wso2.msf4j.MicroservicesRunner;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

public class CoffeeServiceTest {
    private static URI baseURI;
    private static final int TEST_PORT = Application.DEFAULT_PORT + 1;
    private static final Gson GSON = new Gson();
    private static final Menu menu = new Menu(new DataStoreMock());
    private static final MicroservicesRunner microservicesRunner = new MicroservicesRunner(TEST_PORT);

    @BeforeClass
    public static void setup() throws Exception {
        baseURI = URI.create(String.format("http://%s:%d", "localhost", TEST_PORT));
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
    public void testThatReadyCoffeeReturnsReady() throws Exception {
        URL url = baseURI.resolve("/order/321").toURL();
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        String response = getResponse(urlConnection);
        Assert.assertEquals("{\"status\":\"READY\"}", response);
    }

    @Test
    public void testOrderCoffeeReturnsHttpResponseStatusCreated() throws Exception {
        URL url = baseURI.resolve("/order/long-black").toURL();
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        setPostParams(urlConnection, "{\"size\":\"large\", \"extras\":[\"skim-milk\",\"sugar\"]}");
        Assert.assertEquals(HttpResponseStatus.CREATED.code(), urlConnection.getResponseCode());
    }

    @Test
    public void testOrderCoffeeReturnsOrderResponse() throws Exception {
        URL url = baseURI.resolve("/order/long_black").toURL();
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        setPostParams(urlConnection, "{\"size\":\"large\", \"extras\":[\"skim-milk\",\"sugar\"]}");
        String response = getResponse(urlConnection);
        Assert.assertEquals("\"{\\\"order\\\":321,\\\"wait_time\\\":5}\"", response);
    }

    @AfterClass
    public static void tearDown() throws Exception {
        microservicesRunner.stop();
    }

    protected String getResponse(HttpURLConnection urlConnection) throws IOException {
        return new String(ByteStreams.toByteArray(urlConnection.getInputStream()), Charsets.UTF_8);
    }

    protected void setPostParams(HttpURLConnection urlConnection, String params) throws IOException {
        urlConnection.setDoOutput(true);
        urlConnection.setRequestMethod(HttpMethod.POST.name());
        urlConnection.setRequestProperty("Content-Type", "application/json");
        DataOutputStream wr = new DataOutputStream(urlConnection.getOutputStream());
        wr.writeBytes(params);
        wr.flush();
        wr.close();
    }
}
