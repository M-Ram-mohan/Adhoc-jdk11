package rest;

import io.swagger.annotations.Api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/dropwizard")
@Api(value = "/dropwizard")
public class DropwizardResource {
    @GET
    @Path("/sayHello")
    @Produces(MediaType.APPLICATION_JSON)
    public String sayHello() {
        return "Hello, Dropwizard";
    }
}
