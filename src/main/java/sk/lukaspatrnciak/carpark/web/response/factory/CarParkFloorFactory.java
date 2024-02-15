package sk.stuba.fei.uim.vsa.pr2.web.response.factory;

import sk.stuba.fei.uim.vsa.pr2.domain.CarParkFloor;
import sk.stuba.fei.uim.vsa.pr2.domain.ParkingSpot;
import sk.stuba.fei.uim.vsa.pr2.web.response.CarParkFloorDto;
import sk.stuba.fei.uim.vsa.pr2.web.response.ParkingSpotDto;

import java.util.ArrayList;
import java.util.List;

public class CarParkFloorFactory {

    public static CarParkFloor transformToEntity(CarParkFloorDto carParkFloorDto) {
        if (carParkFloorDto.getIdentifier() == null || carParkFloorDto.getCarPark() == null) {
            return null;
        }

        CarParkFloor carParkFloor = new CarParkFloor(carParkFloorDto.getCarPark(), carParkFloorDto.getIdentifier());

        if (carParkFloorDto.getId() != null) {
            carParkFloor.setId(carParkFloorDto.getId());
        }

        if (carParkFloorDto.getCarPark() == null || carParkFloorDto.getSpots().isEmpty()) {
            carParkFloor.setSpots(new ArrayList<>());

        } else {
            List<ParkingSpot> spots = new ArrayList<>();

            for (ParkingSpotDto spotDto : carParkFloorDto.getSpots()) {
                spots.add(ParkingSpotFactory.transformToEntity(spotDto, carParkFloor));
            }

            carParkFloor.setSpots(spots);
        }

        return carParkFloor;
    }

    public static CarParkFloorDto transformToDto(CarParkFloor carParkFloor) {
        CarParkFloorDto carParkFloorDto = new CarParkFloorDto(carParkFloor.getId(), carParkFloor.getFloorIdentifier(), carParkFloor.getCarParkId());
        List<ParkingSpotDto> parkingSpotDtoList = new ArrayList<>();

        for(ParkingSpot parkingSpot: carParkFloor.getSpots()) {
            parkingSpotDtoList.add(ParkingSpotFactory.transformToDto(parkingSpot));
        }

        carParkFloorDto.setSpots(parkingSpotDtoList);

        return carParkFloorDto;
    }
}
