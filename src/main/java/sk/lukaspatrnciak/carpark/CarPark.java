package sk.lukaspatrnciak.carpark;

import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.core.UriBuilder;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import sk.lukaspatrnciak.carpark.services.Authentification;
import sk.lukaspatrnciak.carpark.services.CarParkService;

import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Project2 {

    private static Long id = 2121L;
    private static String email = "admin@vsa.sk";

    public static final Logger LOGGER = Logger.getLogger(Project2.class.getName());
    public static final String BASE_URI = "http://localhost/";
    public static final int PORT = 8080;
    public static final Class<? extends Application> APPLICATION_CLASS = Project2Application.class;

    public static void main(String[] args) {
        try {
            final HttpServer server = startServer();
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                try {
                    System.out.println("Shutting down the application...");
                    server.shutdownNow();
                    System.out.println("Exiting");
                } catch (Exception e) {
                    LOGGER.log(Level.SEVERE, null, e);
                }
            }));
            System.out.println("Last steps of setting up the application...");
            postStart();
            System.out.println("Application started.\nStop the application using CRL+C");
            Thread.currentThread().join();
        } catch (InterruptedException e) {
            LOGGER.log(Level.SEVERE, null, e);
        }
    }

    public static HttpServer startServer() {
        final ResourceConfig config = ResourceConfig.forApplicationClass(APPLICATION_CLASS);
        URI baseUri = UriBuilder.fromUri(BASE_URI).port(PORT).build();
        LOGGER.info("Starting Grizzly2 HTTP server...");
        LOGGER.info("Server listening on " + BASE_URI + ":" + PORT);
        return GrizzlyHttpServerFactory.createHttpServer(baseUri, config);
    }

    public static void postStart() {
        CarParkService carParkService = new CarParkService();

        Authentification.startAuth(email, id);

        if(carParkService.getUser(id) == null) {
            carParkService.createUser(id, "Lukas", "Patrnciak", email);
        }
    }

}
