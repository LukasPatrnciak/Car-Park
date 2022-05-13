package sk.stuba.fei.uim.vsa.pr2.web;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import sk.stuba.fei.uim.vsa.pr2.domain.ParkingSpot;
import sk.stuba.fei.uim.vsa.pr2.services.Authentification;
import sk.stuba.fei.uim.vsa.pr2.services.CarParkService;
import sk.stuba.fei.uim.vsa.pr2.web.response.ParkingSpotDto;
import sk.stuba.fei.uim.vsa.pr2.web.response.factory.ParkingSpotFactory;

@Path("/parkingspots")
public class ParkingSpotResource {

    @HeaderParam(HttpHeaders.AUTHORIZATION) String authorization;

    private final CarParkService carParkService = new CarParkService();

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getParkingSpotById(@PathParam("id") Long id) {
        if(!Authentification.isAuthorised(authorization)) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        if(id == null || id <= 0) {
            return Response.status(Response.Status.BAD_REQUEST).build();

        } else {
            ParkingSpot parkingSpot = carParkService.getParkingSpot(id);

            ParkingSpotDto parkingSpotDto = ParkingSpotFactory.transformToDto(parkingSpot);

            return Response.status(Response.Status.OK).entity(parkingSpotDto).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deleteParkingSpotById(@PathParam("id") Long id) {
        if(!Authentification.isAuthorised(authorization)) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        if(id == null || id <= 0) {
            return Response.status(Response.Status.BAD_REQUEST).build();

        } else {
            ParkingSpot parkingSpot = carParkService.deleteParkingSpot(id);

            if(parkingSpot == null) {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }

            return Response.status(Response.Status.NO_CONTENT).build();
        }
    }

}
