package sk.stuba.fei.uim.vsa.pr2.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import sk.stuba.fei.uim.vsa.pr2.domain.Car;
import sk.stuba.fei.uim.vsa.pr2.domain.CarParkFloor;
import sk.stuba.fei.uim.vsa.pr2.domain.Reservation;
import sk.stuba.fei.uim.vsa.pr2.services.Authentification;
import sk.stuba.fei.uim.vsa.pr2.services.CarParkService;
import sk.stuba.fei.uim.vsa.pr2.web.response.ReservationDto;
import sk.stuba.fei.uim.vsa.pr2.web.response.factory.ReservationFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Path("/reservations")
public class ReservationResource {

    @HeaderParam(HttpHeaders.AUTHORIZATION) String authorization;

    private final CarParkService carParkService = new CarParkService();
    private final ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getReservations(@QueryParam("user") Long user, @QueryParam("spot") Long spot, @QueryParam("date") Date date) {
        if(!Authentification.isAuthorised(authorization)) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        if(spot == null && date == null) {
            List<Reservation> reservationList;
            List<ReservationDto> reservationListDto = new ArrayList<>();

            reservationList = carParkService.getMyReservations(user);

            for(Reservation reservation: reservationList) {
                ReservationDto reservationDto = ReservationFactory.transformToDto(reservation);
                reservationListDto.add(reservationDto);
            }

            return Response.status(Response.Status.OK).entity(reservationListDto).build();

        } else if(user == null) {
            List<Reservation> reservationList;
            List<ReservationDto> reservationListDto = new ArrayList<>();

            reservationList = carParkService.getReservations(spot, date);

            for(Reservation reservation: reservationList) {
                ReservationDto reservationDto = ReservationFactory.transformToDto(reservation);
                reservationListDto.add(reservationDto);
            }

            return Response.status(Response.Status.OK).entity(reservationListDto).build();

        } else {
            List<Reservation> reservationList;
            List<ReservationDto> reservationListDto = new ArrayList<>();
            List<ReservationDto> reservationListUserIdDto = new ArrayList<>();

            reservationList = carParkService.getReservations(spot, date);

            for(Reservation reservation: reservationList) {
                ReservationDto reservationDto = ReservationFactory.transformToDto(reservation);
                reservationListDto.add(reservationDto);
            }

            for(ReservationDto reservationDto: reservationListDto) {
                if (user.equals(reservationDto.getCar().getOwner().getId())) {
                    reservationListUserIdDto.add(reservationDto);
                }
            }


            return Response.status(Response.Status.OK).entity(reservationListUserIdDto).build();
        }
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getReservationById(@PathParam("id") Long id) {
        if(!Authentification.isAuthorised(authorization)) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        if(id == null || id <= 0) {
            return Response.status(Response.Status.BAD_REQUEST).build();

        } else {
            Reservation reservation = carParkService.getReservation(id);

            ReservationDto reservationDto = ReservationFactory.transformToDto(reservation);

            return Response.status(Response.Status.OK).entity(reservationDto).build();
        }
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response postReservation(String body) {
        if(!Authentification.isAuthorised(authorization)) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        try {
            ReservationDto reservationDto = mapper.readValue(body, ReservationDto.class);

            CarParkFloor carParkFloor = carParkService.getCarParkFloor(reservationDto.getSpot().getCarParkFloor());

            Reservation reservation = ReservationFactory.transformToEntity(reservationDto, carParkFloor);

            if(reservation== null) {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }

            Reservation reservationToSave = carParkService.createReservation(reservation.getId(), reservation.getParkingSpotId(), reservation.getCarId());

            if(reservationToSave == null) {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }

            ReservationDto newReservationDto = ReservationFactory.transformToDto(reservationToSave);

            return Response.status(Response.Status.OK).entity(newReservationDto).build();

        } catch (JsonProcessingException e) {
            System.out.println(e.getMessage());

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }


}
