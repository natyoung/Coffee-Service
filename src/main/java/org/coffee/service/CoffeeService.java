package org.coffee.service;

import org.coffee.domain.beans.Order;
import org.coffee.domain.Menu;
import org.coffee.service.params.OrderDetails;

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
    @Consumes("application/json")
    @Produces("application/json")
    public Response orderCoffee(@PathParam("coffee_name") String coffeeName,
                                final OrderDetails orderDetails) {
        Order order = new Order(coffeeName, orderDetails.size, orderDetails.extras);
        String orderResult = menu.createOrder(order);
        return Response.status(Response.Status.CREATED)
                .entity(orderResult)
                .build();
    }
}
