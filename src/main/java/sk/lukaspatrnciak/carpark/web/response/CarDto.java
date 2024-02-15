package sk.stuba.fei.uim.vsa.pr2.web.response;

import sk.stuba.fei.uim.vsa.pr2.domain.Reservation;

import java.util.List;

public class CarDto {

    private Long id;
    private String brand;
    private String model;
    private String vrp;
    private String colour;
    private Object type;
    private UserDto owner;
    private List<Reservation> reservations;

    public CarDto(Long id, String brand, String model, String vrp, String colour, Object type, UserDto owner) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.vrp = vrp;
        this.colour = colour;
        this.type = type;
        this.owner = owner;
    }

    public CarDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getVrp() {
        return vrp;
    }

    public void setVrp(String vrp) {
        this.vrp = vrp;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public Object getType() {
        return type;
    }

    public void setType(Object type) {
        this.type = type;
    }

    public UserDto getOwner() {
        return owner;
    }

    public void setOwner(UserDto owner) {
        this.owner = owner;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

}
