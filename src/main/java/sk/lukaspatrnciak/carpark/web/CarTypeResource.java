package sk.stuba.fei.uim.vsa.pr2.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import sk.stuba.fei.uim.vsa.pr2.domain.CarType;
import sk.stuba.fei.uim.vsa.pr2.services.Authentification;
import sk.stuba.fei.uim.vsa.pr2.services.CarParkService;
import sk.stuba.fei.uim.vsa.pr2.web.response.CarTypeDto;
import sk.stuba.fei.uim.vsa.pr2.web.response.factory.CarTypeFactory;

import java.util.ArrayList;
import java.util.List;

@Path("/cartypes")
public class CarTypeResource {

    @HeaderParam(HttpHeaders.AUTHORIZATION) String authorization;

    private final CarParkService carParkService = new CarParkService();
    private final ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCarTypes(@QueryParam("name") String name) {
        if(!Authentification.isAuthorised(authorization)) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        if(name == null) {
            List<CarType> carTypeList;
            List<CarTypeDto> carTypeListDto = new ArrayList<>();

            carTypeList = carParkService.getCarTypes();

            for(CarType carType: carTypeList) {
                CarTypeDto carTypeDto = CarTypeFactory.transformToDto(carType);
                carTypeListDto.add(carTypeDto);
            }

            return Response.status(Response.Status.OK).entity(carTypeListDto).build();

        } else {
            CarType carType;
            List<CarTypeDto> carTypeListDto = new ArrayList<>();

            carType = carParkService.getCarType(name);

            CarTypeDto carTypeDto = CarTypeFactory.transformToDto(carType);
            carTypeListDto.add(carTypeDto);

            return Response.status(Response.Status.OK).entity(carTypeListDto).build();
        }
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCarTypeById(@PathParam("id") Long id) {
        if(!Authentification.isAuthorised(authorization)) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        if(id == null || id <= 0) {
            return Response.status(Response.Status.BAD_REQUEST).build();

        } else {
            CarType carType = carParkService.getCarType(id);

            CarTypeDto carTypeDto = CarTypeFactory.transformToDto(carType);

            return Response.status(Response.Status.OK).entity(carTypeDto).build();
        }
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response postCarType(String body) {
        if(!Authentification.isAuthorised(authorization)) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        try {
            CarTypeDto carTypeDto = mapper.readValue(body, CarTypeDto.class);
            CarType carType = CarTypeFactory.transformToEntity(carTypeDto);

            if(carType == null) {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }

            CarType carTypeToSave = carParkService.createCarType(carType.getId(), carType.getName());

            if(carTypeToSave == null) {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }

            CarTypeDto newCarTypeDto = CarTypeFactory.transformToDto(carTypeToSave);

            return Response.status(Response.Status.OK).entity(newCarTypeDto).build();

        } catch (JsonProcessingException e) {
            System.out.println(e.getMessage());

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deleteCarTypeById(@PathParam("id") Long id) {
        if(!Authentification.isAuthorised(authorization)) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        if(id == null || id <= 0) {
            return Response.status(Response.Status.BAD_REQUEST).build();

        } else {
            CarType carType = carParkService.deleteCarType(id);

            if(carType == null) {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }

            return Response.status(Response.Status.NO_CONTENT).build();
        }
    }

}
