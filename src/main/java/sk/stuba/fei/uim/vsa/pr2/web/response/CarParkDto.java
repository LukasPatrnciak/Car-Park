package sk.stuba.fei.uim.vsa.pr2.web.response;

import sk.stuba.fei.uim.vsa.pr2.domain.CarPark;
import sk.stuba.fei.uim.vsa.pr2.domain.CarParkFloor;

import java.util.ArrayList;
import java.util.List;

public class CarParkDto {

    private Long id;
    private String name;
    private String address;
    private Number prices;
    private List<CarParkFloorDto> floors;

    public CarParkDto(Long id, String name, String address, Number prices) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.prices = prices;
    }

    public CarParkDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Number getPrices() {
        return prices;
    }

    public void setPrices(Number prices) {
        this.prices = prices;
    }

    public List<CarParkFloorDto> getFloors() {
        return floors;
    }

    public void setFloors(List<CarParkFloorDto> floors) {
        this.floors = floors;
    }

}
