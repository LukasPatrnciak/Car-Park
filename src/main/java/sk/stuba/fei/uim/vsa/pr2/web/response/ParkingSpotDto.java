package sk.stuba.fei.uim.vsa.pr2.web.response;

import sk.stuba.fei.uim.vsa.pr2.domain.Reservation;
import java.util.List;

public class ParkingSpotDto {

    private Long id;
    private String identifier;
    private String carParkFloor;
    private Long carPark;
    private CarTypeDto type;
    private Boolean free;
    private List<ReservationDto> reservations;

    public ParkingSpotDto(Long id, String identifier, String carParkFloor, Long carPark, CarTypeDto type, Boolean free) {
        this.id = id;
        this.identifier = identifier;
        this.carParkFloor = carParkFloor;
        this.carPark = carPark;
        this.type = type;
        this.free = free;
    }

    public ParkingSpotDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getCarParkFloor() {
        return carParkFloor;
    }

    public void setCarParkFloor(String carParkFloor) {
        this.carParkFloor = carParkFloor;
    }

    public Long getCarPark() {
        return carPark;
    }

    public void setCarPark(Long carPark) {
        this.carPark = carPark;
    }

    public CarTypeDto getType() {
        return type;
    }

    public void setType(CarTypeDto type) {
        this.type = type;
    }

    public Boolean getFree() {
        return free;
    }

    public void setFree(Boolean free) {
        this.free = free;
    }

    public List<ReservationDto> getReservations() {
        return reservations;
    }

    public void setReservations(List<ReservationDto> reservations) {
        this.reservations = reservations;
    }

}
