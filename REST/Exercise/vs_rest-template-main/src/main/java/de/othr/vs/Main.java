package de.othr.vs;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.io.IOException;
import java.net.URI;

public class Main {

    public static final String BASE_URI = "http://0.0.0.0:8080/api";

    public static HttpServer startServer() {

        final ResourceConfig config = new ResourceConfig()
                .packages("de.othr.vs.service");

        return GrizzlyHttpServerFactory.createHttpServer(
                URI.create(BASE_URI),
                config
        );
    }

    public static void main(String[] args) throws IOException {

        final HttpServer server = startServer();

        System.out.println("Server gestartet:");
        System.out.println(BASE_URI);

        System.out.println("ENTER zum Stoppen...");
        System.in.read();

        server.shutdownNow();
    }
}