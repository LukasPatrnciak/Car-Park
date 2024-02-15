package sk.stuba.fei.uim.vsa.pr2.web.response.factory;

import sk.stuba.fei.uim.vsa.pr2.domain.CarType;
import sk.stuba.fei.uim.vsa.pr2.web.response.CarTypeDto;

public class CarTypeFactory {

    public static CarType transformToEntity(CarTypeDto carTypeDto) {
        if(carTypeDto.getName() == null) {
            return null;
        }

        CarType carType = new CarType(carTypeDto.getId(), carTypeDto.getName());

        if(carTypeDto.getId() != null) {
            carType.setId(carTypeDto.getId());
        }

        return carType;
    }

    public static CarTypeDto transformToDto(CarType carType) {
        CarTypeDto carTypeDto = new CarTypeDto(carType.getId(), carType.getName());

        return carTypeDto;
    }

}
