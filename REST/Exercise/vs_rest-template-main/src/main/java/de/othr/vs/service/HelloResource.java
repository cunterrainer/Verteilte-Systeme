package de.othr.vs.service;

import de.othr.vs.entity.ExampleEntity;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

@Path("/example")
public class HelloResource {

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public ExampleEntity hello() {
        ExampleEntity reply = new ExampleEntity();
        reply.setAttrib1("Hello World");
        reply.setAttrib2(42);
        return reply;
    }
}
