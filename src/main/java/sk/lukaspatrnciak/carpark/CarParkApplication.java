package sk.lukaspatrnciak.carpark;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import sk.lukaspatrnciak.carpark.web.*;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/api")
public class Project2Application extends Application {

    static final Set<Class<?>> appClasses = new HashSet<>();

    static {
        appClasses.add(CarParkResource.class);
        appClasses.add(CarParkFloorResource.class);
        appClasses.add(CarResource.class);
        appClasses.add(CarTypeResource.class);
        appClasses.add(ParkingSpotResource.class);
        appClasses.add(ReservationResource.class);
        appClasses.add(UserResource.class);

    }

    @Override
    public Set<Class<?>> getClasses() {
        return appClasses;
    }

}
