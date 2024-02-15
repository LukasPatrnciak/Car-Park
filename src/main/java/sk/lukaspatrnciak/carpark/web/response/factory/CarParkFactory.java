package sk.stuba.fei.uim.vsa.pr2.web.response.factory;

import sk.stuba.fei.uim.vsa.pr2.domain.CarPark;
import sk.stuba.fei.uim.vsa.pr2.domain.CarParkFloor;
import sk.stuba.fei.uim.vsa.pr2.web.response.CarParkDto;
import sk.stuba.fei.uim.vsa.pr2.web.response.CarParkFloorDto;

import java.util.ArrayList;
import java.util.List;

public class CarParkFactory {

    public static CarPark transformToEntity(CarParkDto carParkDto) {
        if(carParkDto.getName() == null || carParkDto.getAddress() == null || carParkDto.getPrices() == null) {
            return null;
        }

        CarPark carPark = new CarPark(null, carParkDto.getName(), carParkDto.getAddress(), carParkDto.getPrices().intValue());

        if(carParkDto.getId() != null) {
            carPark.setId(carParkDto.getId());
        }

        if(carParkDto.getFloors() == null || carParkDto.getFloors().isEmpty()) {
            carPark.setFloors(new ArrayList<>());

        } else {
            List<CarParkFloor> floors = new ArrayList<>();

            for(CarParkFloorDto floorDto: carParkDto.getFloors()) {
                floors.add(CarParkFloorFactory.transformToEntity(floorDto));
            }

            carPark.setFloors(floors);
        }

        return carPark;
    }

    public static CarParkDto transformToDto(CarPark carPark) {
        CarParkDto carParkDto = new CarParkDto(carPark.getId(), carPark.getName(), carPark.getAddress(), carPark.getPricePerHour());
        List<CarParkFloorDto> carParkFloorList = new ArrayList<>();

        for(CarParkFloor carParkFloor: carPark.getFloors()) {
            carParkFloorList.add(CarParkFloorFactory.transformToDto(carParkFloor));
        }

        carParkDto.setFloors(carParkFloorList);

        return carParkDto;
    }

}
