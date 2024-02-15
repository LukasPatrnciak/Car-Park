package sk.stuba.fei.uim.vsa.pr2.domain;

import javax.persistence.*;

@Entity
@Table(name = "\"CAR\"")
@NamedQueries({
        @NamedQuery(name = "Car.findAll", query = "SELECT car FROM Car car"),
        @NamedQuery(name = "Car.findByUserId", query = "SELECT car FROM Car car WHERE car.userId = :id"),
        @NamedQuery(name = "Car.findById", query = "SELECT car FROM Car car WHERE car.id = :id"),
        @NamedQuery(name = "Car.findByPlate", query = "SELECT car FROM Car car WHERE car.vehicleRegistrationPlate = :plate")
})
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    private Long userId;
    private String brand;
    private String model;
    private String colour;
    private String vehicleRegistrationPlate;

    private Long carTypeId;

    public Car(Long id, Long userId, String brand, String model, String colour, String vehicleRegistrationPlate) {
        this.id = id;
        this.userId = userId;
        this.brand = brand;
        this.model = model;
        this.colour = colour;
        this.vehicleRegistrationPlate = vehicleRegistrationPlate;
    }

    public Car(Long id, Long userId, String brand, String model, String colour, String vehicleRegistrationPlate, Long carTypeId) {
        this.id = id;
        this.userId = userId;
        this.brand = brand;
        this.model = model;
        this.colour = colour;
        this.vehicleRegistrationPlate = vehicleRegistrationPlate;
        this.carTypeId = carTypeId;
    }

    public Car() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public String getVehicleRegistrationPlate() {
        return vehicleRegistrationPlate;
    }

    public void setVehicleRegistrationPlate(String vehicleRegistrationPlate) {
        this.vehicleRegistrationPlate = vehicleRegistrationPlate;
    }

    public Long getCarTypeId() {
        return carTypeId;
    }

    public void setCarTypeId(Long carTypeId) {
        this.carTypeId = carTypeId;
    }
}