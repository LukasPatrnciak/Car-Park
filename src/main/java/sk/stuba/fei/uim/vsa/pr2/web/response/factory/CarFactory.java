package sk.stuba.fei.uim.vsa.pr2.web.response.factory;

import sk.stuba.fei.uim.vsa.pr2.domain.Car;
import sk.stuba.fei.uim.vsa.pr2.web.response.CarDto;

public class CarFactory {

    public static Car transformToEntity(CarDto carDto) {
        if(carDto.getBrand() == null || carDto.getColour() == null || carDto.getModel() == null || carDto.getType() == null || carDto.getVrp() == null) {
            return null;
        }

        Car car = null;

        return car;
    }

    public static CarDto transformToDto(Car car) {
        CarDto carDto = new CarDto(car.getId(), car.getBrand(), car.getModel(), car.getVehicleRegistrationPlate(), car.getColour(), null, null);

        return carDto;
    }

}
