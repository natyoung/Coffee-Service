package org.coffee.service;

import com.sun.org.glassfish.gmbal.ParameterNames;
import model.Coffee;

import javax.ws.rs.*;
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

    @POST
    @Path("/order/{coffee_name}")
    @Produces("application/json")
    public Response orderCoffeeByName(@PathParam("coffee_name") String name) {
        return Response.status(Response.Status.CREATED)
                .entity("READY")
                .build();
    }

}
