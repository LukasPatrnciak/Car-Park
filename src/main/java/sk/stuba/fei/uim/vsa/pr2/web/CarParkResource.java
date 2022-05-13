package sk.stuba.fei.uim.vsa.pr2.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import sk.stuba.fei.uim.vsa.pr2.domain.CarPark;
import sk.stuba.fei.uim.vsa.pr2.domain.CarParkFloor;
import sk.stuba.fei.uim.vsa.pr2.services.Authentification;
import sk.stuba.fei.uim.vsa.pr2.services.CarParkService;
import sk.stuba.fei.uim.vsa.pr2.web.response.CarParkDto;
import sk.stuba.fei.uim.vsa.pr2.web.response.CarParkFloorDto;
import sk.stuba.fei.uim.vsa.pr2.web.response.factory.CarParkFactory;
import sk.stuba.fei.uim.vsa.pr2.web.response.factory.CarParkFloorFactory;

import java.util.ArrayList;
import java.util.List;

@Path("/carparks")
public class CarParkResource {

    @HeaderParam(HttpHeaders.AUTHORIZATION) String authorization;

    private final CarParkService carParkService = new CarParkService();
    private final ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCarParks(@QueryParam("name") String name) {
        if(!Authentification.isAuthorised(authorization)) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        if(name == null) {
            List<CarPark> carParkList;
            List<CarParkDto> carParkListDto = new ArrayList<>();

            carParkList = carParkService.getCarParks();

            for(CarPark carPark: carParkList) {
                CarParkDto carParkDto = CarParkFactory.transformToDto(carPark);
                carParkListDto.add(carParkDto);
            }

            return Response.status(Response.Status.OK).entity(carParkListDto).build();

        } else {
            CarPark carPark;
            List<CarParkDto> carParkListDto = new ArrayList<>();

            carPark = carParkService.getCarPark(name);

            CarParkDto carParkDto = CarParkFactory.transformToDto(carPark);
            carParkListDto.add(carParkDto);

            return Response.status(Response.Status.OK).entity(carParkListDto).build();
        }
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCarParkById(@PathParam("id") Long id) {
        if(!Authentification.isAuthorised(authorization)) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        if(id == null || id <= 0) {
            return Response.status(Response.Status.BAD_REQUEST).build();

        } else {
            CarPark carPark = carParkService.getCarPark(id);

            CarParkDto carParkDto = CarParkFactory.transformToDto(carPark);

            return Response.status(Response.Status.OK).entity(carParkDto).build();
        }
    }

    @GET
    @Path("/{id}/floors")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFloorsInCarPark(@PathParam("id") Long id) {
        if(!Authentification.isAuthorised(authorization)) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        if(id == null || id <= 0) {
            return Response.status(Response.Status.BAD_REQUEST).build();

        } else {
            List<CarParkFloor> carParkFloorList;
            List<CarParkFloorDto> carParkFloorListDto = new ArrayList<>();

            carParkFloorList = carParkService.getCarParkFloors(id);

            for(CarParkFloor carParkFloor: carParkFloorList) {
                CarParkFloorDto carParkFloorDto = CarParkFloorFactory.transformToDto(carParkFloor);
                carParkFloorListDto.add(carParkFloorDto);
            }

            return Response.status(Response.Status.OK).entity(carParkFloorListDto).build();
        }
    }

    @GET
    @Path("/{id}/floors/{identifier}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFloorsInCarParkByIdentifier(@PathParam("id") Long id, @PathParam("identifier") String floorIdentifier) {
        if(!Authentification.isAuthorised(authorization)) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        if(floorIdentifier.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST).build();

        } else {
            CarParkFloor carParkFloor = carParkService.getCarParkFloor(id, floorIdentifier);

            CarParkFloorDto carParkFloorDto = CarParkFloorFactory.transformToDto(carParkFloor);

            return Response.status(Response.Status.OK).entity(carParkFloorDto).build();
        }
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response postCarPark(String body) {
        if(!Authentification.isAuthorised(authorization)) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        try {
            CarParkDto carParkDto = mapper.readValue(body, CarParkDto.class);
            CarPark carPark = CarParkFactory.transformToEntity(carParkDto);

            if(carPark == null) {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }

            CarPark carParkToSave = carParkService.createCarPark(carPark.getId(), carPark.getName(), carPark.getAddress(), carPark.getPricePerHour());

            if(carParkToSave == null) {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }

            CarParkDto newCarParkDto = CarParkFactory.transformToDto(carParkToSave);

            return Response.status(Response.Status.OK).entity(newCarParkDto).build();

        } catch (JsonProcessingException e) {
            System.out.println(e.getMessage());

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @POST
    @Path("/{id}/floors")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response postCarParkById(@PathParam("id") Long id, String body) {
        if(!Authentification.isAuthorised(authorization)) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        try {
            CarParkFloorDto carParkFloorDto = mapper.readValue(body, CarParkFloorDto.class);
            CarParkFloor carParkFloor = CarParkFloorFactory.transformToEntity(carParkFloorDto);

            if(carParkFloor == null) {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }

            CarParkFloor carParkFloorToSave = carParkService.createCarParkFloor(id, carParkFloor.getFloorIdentifier());

            if(carParkFloorToSave == null) {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }

            CarParkFloorDto newCarParkFloorDto = CarParkFloorFactory.transformToDto(carParkFloorToSave);

            return Response.status(Response.Status.OK).entity(newCarParkFloorDto).build();

        } catch (JsonProcessingException e) {
            System.out.println(e.getMessage());

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deleteCarParkById(@PathParam("id") Long id) {
        if(!Authentification.isAuthorised(authorization)) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        if(id == null || id <= 0) {
            return Response.status(Response.Status.BAD_REQUEST).build();

        } else {
            CarPark carPark = carParkService.deleteCarPark(id);

            if(carPark == null) {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }

            return Response.status(Response.Status.NO_CONTENT).build();
        }
    }

    @DELETE
    @Path("/{id}/floors/{identifier}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteFloorsInCarParkByIdentifier(@PathParam("id") Long id, @PathParam("identifier") String floorIdentifier) {
        if(!Authentification.isAuthorised(authorization)) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        if(floorIdentifier.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST).build();

        } else {
            CarParkFloor carParkFloor = carParkService.deleteCarParkFloor(id, floorIdentifier);

            if(carParkFloor == null) {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }

            return Response.status(Response.Status.NO_CONTENT).build();
        }
    }

}
