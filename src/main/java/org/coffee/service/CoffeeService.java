package org.coffee.service;

import org.coffee.domain.beans.Coffee;
import org.coffee.domain.beans.Order;
import org.coffee.domain.Menu;
import org.coffee.service.params.OrderDetails;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;

@Path("/")
public class CoffeeService {
    private final Menu menu;

    public CoffeeService(Menu menu) {
        this.menu = menu;
    }

    @GET
    @Path("/menu")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMenu() {
        return Response.status(Response.Status.OK)
                .entity(menu.getCoffees())
                .build();
    }

    @GET
    @Path("/order/{order_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOrder(@PathParam("order_id") long orderId) {
        String orderStatus = menu.getOrderStatus(orderId);
        if(orderStatus == null) {
            return Response.status(Response.Status.NOT_FOUND).
                    entity("No order was found with that ID").
                    build();
        }
        return Response.status(Response.Status.OK)
                .entity(orderStatus)
                .build();
    }

    @POST
    @Path("/order/{coffee_name}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response orderCoffee(@PathParam("coffee_name") String coffeeName,
                                final OrderDetails orderDetails) {
        Order order = new Order(coffeeName, orderDetails.size, orderDetails.extras);
        String orderResult = menu.createOrder(order);
        return Response.status(Response.Status.CREATED)
                .entity(orderResult)
                .build();
    }
}
