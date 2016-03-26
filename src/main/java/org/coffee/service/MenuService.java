package org.coffee.service;

import model.Coffee;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Map;

@Path("/menu")
public class MenuService {
    private final Map<String, List<Coffee>> menu;

    public MenuService(Map<String, List<Coffee>> menu) {
        this.menu = menu;
    }

    @GET
    @Path("/")
    @Produces("application/json")
    public Response getMenu() {
        return Response.status(Response.Status.OK)
                .entity(menu)
                .build();
    }
}
