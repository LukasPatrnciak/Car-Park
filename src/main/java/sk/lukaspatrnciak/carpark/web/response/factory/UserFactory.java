package sk.stuba.fei.uim.vsa.pr2.web.response.factory;

import sk.stuba.fei.uim.vsa.pr2.domain.Car;
import sk.stuba.fei.uim.vsa.pr2.domain.User;
import sk.stuba.fei.uim.vsa.pr2.web.response.CarDto;
import sk.stuba.fei.uim.vsa.pr2.web.response.UserDto;

import java.util.ArrayList;
import java.util.List;

public class UserFactory {

    public static User transformToEntity(UserDto userDto) {
        if(userDto.getFirstName() == null || userDto.getLastName() == null || userDto.getEmail() == null) {
            return null;
        }

        User user = new User(null, userDto.getFirstName(), userDto.getLastName(), userDto.getEmail());

        if(userDto.getId() != null) {
            user.setId(userDto.getId());
        }

        if(userDto.getCars() == null || userDto.getCars().isEmpty()) {
            user.setOwnedCars(new ArrayList<>());

        } else {
            List<Car> cars = new ArrayList<>();

            for(CarDto carDto: userDto.getCars()) {
                cars.add(CarFactory.transformToEntity(carDto));
            }

            user.setOwnedCars(cars);
        }

        return user;
    }

    public static UserDto transformToDto(User user) {
        UserDto userDto = new UserDto(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail());
        List<CarDto> carList = new ArrayList<>();

        for(Car car: user.getOwnedCars()) {
            carList.add(CarFactory.transformToDto(car));
        }

        userDto.setCars(carList);

        return userDto;
    }

}
