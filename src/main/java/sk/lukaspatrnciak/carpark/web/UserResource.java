package sk.stuba.fei.uim.vsa.pr2.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import sk.stuba.fei.uim.vsa.pr2.domain.User;
import sk.stuba.fei.uim.vsa.pr2.services.Authentification;
import sk.stuba.fei.uim.vsa.pr2.services.CarParkService;
import sk.stuba.fei.uim.vsa.pr2.web.response.UserDto;
import sk.stuba.fei.uim.vsa.pr2.web.response.factory.UserFactory;

import java.util.ArrayList;
import java.util.List;

@Path("/users")
public class UserResource {
    @HeaderParam(HttpHeaders.AUTHORIZATION) String authorization;

    private final CarParkService carParkService = new CarParkService();
    private final ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsers(@QueryParam("email") String email) {
        if(!Authentification.isAuthorised(authorization)) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        if(email == null) {
            List<User> userList;
            List<UserDto> userListDto = new ArrayList<>();

            userList = carParkService.getUsers();

            for(User user: userList) {
                UserDto userDto = UserFactory.transformToDto(user);
                userListDto.add(userDto);
            }

            return Response.status(Response.Status.OK).entity(userListDto).build();

        } else {
            User user;
            List<UserDto> userListDto = new ArrayList<>();

            user = carParkService.getUser(email);

            UserDto userDto = UserFactory.transformToDto(user);
            userListDto.add(userDto);

            return Response.status(Response.Status.OK).entity(userListDto).build();
        }
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserById(@PathParam("id") Long id) {
        if(!Authentification.isAuthorised(authorization)) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        if(id == null || id <= 0) {
            return Response.status(Response.Status.BAD_REQUEST).build();

        } else {
            User user = carParkService.getUser(id);

            UserDto userDto = UserFactory.transformToDto(user);

            return Response.status(Response.Status.OK).entity(userDto).build();
        }
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response postUser(String body) {
        if(!Authentification.isAuthorised(authorization)) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        try {
            UserDto userDto = mapper.readValue(body, UserDto.class);
            User user = UserFactory.transformToEntity(userDto);

            if(user == null) {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }

            User userToSave = carParkService.createUser(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail());

            if(userToSave == null) {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }

            UserDto newUserDto = UserFactory.transformToDto(userToSave);

            return Response.status(Response.Status.OK).entity(newUserDto).build();

        } catch (JsonProcessingException e) {
            System.out.println(e.getMessage());

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deleteUserById(@PathParam("id") Long id) {
        if(!Authentification.isAuthorised(authorization)) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        if(id == null || id <= 0) {
            return Response.status(Response.Status.BAD_REQUEST).build();

        } else {
            User user = carParkService.deleteUser(id);

            if(user == null) {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }

            return Response.status(Response.Status.NO_CONTENT).build();
        }
    }

}
