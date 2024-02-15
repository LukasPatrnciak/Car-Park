package sk.stuba.fei.uim.vsa.pr2.web.response.factory;

import sk.stuba.fei.uim.vsa.pr2.domain.*;
import sk.stuba.fei.uim.vsa.pr2.web.response.ReservationDto;

public class ReservationFactory {

    public static Reservation transformToEntity(ReservationDto reservationDto, CarParkFloor carParkFloor) {
        if(reservationDto.getStart() == null || reservationDto.getEnd() == null || reservationDto.getPrices() == null) {
            return null;
        }

        Reservation reservation = new Reservation(null, CarFactory.transformToEntity(reservationDto.getCar()), reservationDto.getSpot().getId(),  reservationDto.getCar().getId(), reservationDto.getPrices().intValue(), reservationDto.getStart(), reservationDto.getEnd());

        if(reservationDto.getId() != null) {
            reservation.setId(reservationDto.getId());
        }

        if(reservationDto.getSpot() == null || reservationDto.getCar() == null) {
            reservation.setParkingSpotId(reservationDto.getSpot().getId());

            Car car = CarFactory.transformToEntity(reservationDto.getCar());
            ParkingSpot parkingSpot = ParkingSpotFactory.transformToEntity(reservationDto.getSpot(), carParkFloor);

            if(car == null || parkingSpot == null) {
                return null;
            }

            reservation.setCar(car);
            reservation.setParkingSpotId(parkingSpot.getId());
        }

        return reservation;
    }

    public static ReservationDto transformToDto(Reservation reservation) {
        ReservationDto reservationDto = new ReservationDto(reservation.getId(), reservation.getStartAt(), reservation.getEndAt(), reservation.getPrice(), CarFactory.transformToDto(reservation.getCar()), null);

        return reservationDto;
    }

}
