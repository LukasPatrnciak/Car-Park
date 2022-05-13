package sk.stuba.fei.uim.vsa.pr2.domain;

import sk.stuba.fei.uim.vsa.pr2.web.response.CarTypeDto;
import sk.stuba.fei.uim.vsa.pr2.web.response.ReservationDto;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "\"PARKING_SPOT\"")
@NamedQueries({
        @NamedQuery(name = "ParkingSpot.findAll", query = "SELECT pSpot FROM ParkingSpot pSpot WHERE pSpot.carParkId = :id"),
        @NamedQuery(name = "ParkingSpot.findById", query = "SELECT pSpot FROM ParkingSpot pSpot WHERE pSpot.id = :id"),
        @NamedQuery(name = "ParkingSpot.findByIdAndFloor", query = "SELECT pSpot FROM ParkingSpot pSpot WHERE pSpot.carParkId = :id AND pSpot.floorIdentifier = :floor")
})
public class ParkingSpot {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "carusingthespotid")
    private Car carUsingTheSpot;

    private Long carParkId;
    private Long carTypeId;
    private String floorIdentifier;
    private String spotIdentifier;

    private Integer pricePerHour;

    public ParkingSpot(Long carParkId, Long carTypeId, String floorIdentifier, String spotIdentifier, Car carUsingTheSpot, Integer pricePerHour) {
        this.carParkId = carParkId;
        this.carTypeId = carTypeId;
        this.floorIdentifier = floorIdentifier;
        this.spotIdentifier = spotIdentifier;
        this.carUsingTheSpot = carUsingTheSpot;
        this.pricePerHour = pricePerHour;
    }

    public ParkingSpot(Long carParkId, String spotIdentifier, Car carUsingTheSpot, Integer pricePerHour) {
        this.carParkId = carParkId;
        this.spotIdentifier = spotIdentifier;
        this.carUsingTheSpot = carUsingTheSpot;
        this.pricePerHour = pricePerHour;
    }

    public ParkingSpot() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCarParkId() {
        return carParkId;
    }

    public void setCarParkId(Long carParkId) {
        this.carParkId = carParkId;
    }

    public Long getCarTypeId() {
        return carTypeId;
    }

    public void setCarTypeId(Long carTypeId) {
        this.carTypeId = carTypeId;
    }

    public String getFloorIdentifier() {
        return floorIdentifier;
    }

    public void setFloorIdentifier(String floorIdentifier) {
        this.floorIdentifier = floorIdentifier;
    }

    public String getSpotIdentifier() {
        return spotIdentifier;
    }

    public void setSpotIdentifier(String spotIdentifier) {
        this.spotIdentifier = spotIdentifier;
    }

    public Integer getPricePerHour() {
        return pricePerHour;
    }

    public void setPricePerHour(Integer pricePerHour) {
        this.pricePerHour = pricePerHour;
    }

    public Car getCarUsingTheSpot() {
        return carUsingTheSpot;
    }

    public void setCarUsingTheSpot(Car carUsingTheSpot) {
        this.carUsingTheSpot = carUsingTheSpot;
    }

    @Override
    public String toString() {
        return "P A R K O V A C I E  M I E S T O: \n\nID: " + id + "\nID Parkoviska: " + carTypeId + "\nIdentifikator podlazia: " + floorIdentifier + "\nIdentifikator miesta: " + spotIdentifier;
    }

}