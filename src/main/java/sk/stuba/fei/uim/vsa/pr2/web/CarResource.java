package sk.stuba.fei.uim.vsa.pr2.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import sk.stuba.fei.uim.vsa.pr2.domain.Car;
import sk.stuba.fei.uim.vsa.pr2.services.Authentification;
import sk.stuba.fei.uim.vsa.pr2.services.CarParkService;
import sk.stuba.fei.uim.vsa.pr2.web.response.CarDto;
import sk.stuba.fei.uim.vsa.pr2.web.response.factory.CarFactory;

import java.util.ArrayList;
import java.util.List;

@Path("/cars")
public class CarResource {

    @HeaderParam(HttpHeaders.AUTHORIZATION) String authorization;

    private final CarParkService carParkService = new CarParkService();
    private final ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCars(@QueryParam("user") Long user, @QueryParam("vrp") String vrp) {
        if(!Authentification.isAuthorised(authorization)) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        if(user == null && vrp == null) {
            List<Car> carList;
            List<CarDto> carListDto = new ArrayList<>();

            carList = carParkService.getCars();

            for (Car car : carList) {
                CarDto carDto = CarFactory.transformToDto(car);
                carListDto.add(carDto);
            }

            return Response.status(Response.Status.OK).entity(carListDto).build();

        } else if (user == null) {
            Car car = carParkService.getCar(vrp);

            CarDto carDto = CarFactory.transformToDto(car);

            return Response.status(Response.Status.OK).entity(carDto).build();

        } else if (vrp == null) {
            List<Car> carList;
            List<CarDto> carListDto = new ArrayList<>();

            carList = carParkService.getCars(user);

            for (Car car : carList) {
                CarDto carDto = CarFactory.transformToDto(car);
                carListDto.add(carDto);
            }

            return Response.status(Response.Status.OK).entity(carListDto).build();

        } else {
            List<Car> carList;
            List<CarDto> carListDto = new ArrayList<>();

            carList = carParkService.getCars(user);

            for (Car car : carList) {
                CarDto carDto = CarFactory.transformToDto(car);
                carListDto.add(carDto);
            }

            return Response.status(Response.Status.OK).entity(carListDto).build();
        }
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCarById(@PathParam("id") Long id) {
        if(!Authentification.isAuthorised(authorization)) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        if(id == null || id <= 0) {
            return Response.status(Response.Status.BAD_REQUEST).build();

        } else {
            Car car = carParkService.getCar(id);

            CarDto carDto = CarFactory.transformToDto(car);

            return Response.status(Response.Status.OK).entity(carDto).build();
        }
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response postCar(String body) {
        if(!Authentification.isAuthorised(authorization)) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        try {
            CarDto carDto = mapper.readValue(body, CarDto.class);
            Car car = CarFactory.transformToEntity(carDto);

            if(car == null) {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }

            Car carToSave = carParkService.createCar(car.getId(), car.getUserId(), car.getBrand(), car.getModel(), car.getColour(), car.getVehicleRegistrationPlate(), car.getCarTypeId());

            if(carToSave == null) {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }

            CarDto newCarDto = CarFactory.transformToDto(carToSave);

            return Response.status(Response.Status.OK).entity(newCarDto).build();

        } catch (JsonProcessingException e) {
            System.out.println(e.getMessage());

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deleteCarById(@PathParam("id") Long id) {
        if(!Authentification.isAuthorised(authorization)) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        if(id == null || id <= 0) {
            return Response.status(Response.Status.BAD_REQUEST).build();

        } else {
            Car car = carParkService.deleteCar(id);

            if(car == null) {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }

            return Response.status(Response.Status.NO_CONTENT).build();
        }
    }

}
