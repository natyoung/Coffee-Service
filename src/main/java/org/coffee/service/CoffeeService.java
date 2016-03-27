package org.coffee.service;

import org.coffee.domain.Menu;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/")
public class CoffeeService {
    private final Menu menu;

    public CoffeeService(Menu menu) {
        this.menu = menu;
    }

    @GET
    @Path("/menu")
    @Produces("application/json")
    public Response getMenu() {
        return Response.status(Response.Status.OK)
                .entity(menu.getCoffees())
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
                .entity("{\"order\":\"/order/123\",\"wait_time\":\"5\"}")
                .build();
    }
}
