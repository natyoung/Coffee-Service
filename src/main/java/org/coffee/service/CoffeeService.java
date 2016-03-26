package org.coffee.service;

import model.Coffee;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/")
public class CoffeeService {
    private final Map<String, List<Coffee>> menu;

    public CoffeeService(Map<String, List<Coffee>> menu) {
        this.menu = menu;
    }

    @GET
    @Path("/menu")
    @Produces("application/json")
    public Response getMenu() {
        return Response.status(Response.Status.OK)
                .entity(menu)
                .build();
    }

    @GET
    @Path("/order/{order_id}")
    @Produces("application/json")
    public Response getOrderById() {
        return Response.status(Response.Status.OK)
                .entity("READY")
                .build();
    }
}
