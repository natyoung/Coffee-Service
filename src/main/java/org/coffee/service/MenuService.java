package org.coffee.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/menu")
public class MenuService {

    @GET
    @Path("/")
    public String getMenu() {
        System.out.println("GET invoked");
        return "Hello from WSO2 MSF4J";
    }
}
