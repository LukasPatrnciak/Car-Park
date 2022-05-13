package sk.stuba.fei.uim.vsa.pr2.web.response.factory;

import sk.stuba.fei.uim.vsa.pr2.domain.CarPark;
import sk.stuba.fei.uim.vsa.pr2.domain.CarParkFloor;
import sk.stuba.fei.uim.vsa.pr2.domain.CarType;
import sk.stuba.fei.uim.vsa.pr2.domain.ParkingSpot;
import sk.stuba.fei.uim.vsa.pr2.web.response.CarParkFloorDto;
import sk.stuba.fei.uim.vsa.pr2.web.response.ParkingSpotDto;

public class ParkingSpotFactory {

    public static ParkingSpot transformToEntity(ParkingSpotDto parkingSpotDto, CarParkFloor carParkFloor) {
        ParkingSpot parkingSpot = null;
        CarPark carPark = new CarPark();

        if (parkingSpotDto.getIdentifier().isEmpty() || parkingSpotDto.getIdentifier() == null || parkingSpotDto.getCarParkFloor().isEmpty() || parkingSpotDto.getCarParkFloor() == null) {
            return null;
        }

        if(parkingSpotDto.getCarPark() == null) {
            return null;
        }

        CarType carType = CarTypeFactory.transformToEntity(parkingSpotDto.getType());

        if(carType == null) {
            return null;
        }

        parkingSpot = new ParkingSpot(parkingSpotDto.getCarPark(), carType.getId(), carParkFloor.getFloorIdentifier(), parkingSpotDto.getIdentifier(), null, carPark.getPricePerHour());

        if(parkingSpotDto.getId() != null) {
            parkingSpot.setId(parkingSpotDto.getId());
        }

        return parkingSpot;
    }

    public static ParkingSpotDto transformToDto(ParkingSpot parkingSpot) {
        ParkingSpotDto parkingSpotDto = new ParkingSpotDto(parkingSpot.getCarParkId(), parkingSpot.getSpotIdentifier(), parkingSpot.getFloorIdentifier(), parkingSpot.getCarParkId(), null, null);

        return parkingSpotDto;

    }

}
