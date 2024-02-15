package sk.stuba.fei.uim.vsa.pr2.web.response;

import sk.stuba.fei.uim.vsa.pr2.domain.CarParkFloor;
import sk.stuba.fei.uim.vsa.pr2.domain.ParkingSpot;

import java.util.List;

public class CarParkFloorDto {

    private Long id;
    private String identifier;
    private Long carPark;
    private List<ParkingSpotDto> spots;

    public CarParkFloorDto(Long id, String identifier, Long carPark) {
        this.id = id;
        this.identifier = identifier;
        this.carPark = carPark;
    }

    public CarParkFloorDto() {
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

    public Long getCarPark() {
        return carPark;
    }

    public void setCarPark(Long carPark) {
        this.carPark = carPark;
    }

    public List<ParkingSpotDto> getSpots() {
        return spots;
    }

    public void setSpots(List<ParkingSpotDto> spots) {
        this.spots = spots;
    }

}
