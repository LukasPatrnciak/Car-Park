package sk.stuba.fei.uim.vsa.pr2.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "\"CAR_PARK_FLOOR\"")
@NamedQueries({
        @NamedQuery(name = "CarParkFloor.findAll", query = "SELECT cParkFloor FROM CarParkFloor cParkFloor WHERE cParkFloor.carParkId = :id"),
        @NamedQuery(name = "CarParkFloor.findById", query = "SELECT cParkFloor FROM CarParkFloor cParkFloor WHERE cParkFloor.id = :id"),
        @NamedQuery(name = "CarParkFloor.findByIdAndFloor", query = "SELECT cParkFloor FROM CarParkFloor cParkFloor WHERE cParkFloor.id = :id AND cParkFloor.floorIdentifier = :floor"),
        @NamedQuery(name = "CarParkFloor.findByFloor", query = "SELECT cParkFloor FROM CarParkFloor cParkFloor WHERE cParkFloor.floorIdentifier = :floor")
})
public class CarParkFloor {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "floorIdentifier", unique = true)
    private String floorIdentifier;

    @OneToMany(cascade = CascadeType.PERSIST, orphanRemoval = true)
    @JoinColumn(name = "carParkFloorId", nullable = false)
    private List<ParkingSpot> spots;

    private Long carParkId;

    public CarParkFloor(Long carParkId, String floorIdentifier) {
        spots = new ArrayList<>();
        this.floorIdentifier = floorIdentifier;
        this.carParkId = carParkId;
    }

    public CarParkFloor() {
        spots = new ArrayList<>();
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

    public String getFloorIdentifier() {
        return floorIdentifier;
    }

    public void setFloorIdentifier(String floorIdentifier) {
        this.floorIdentifier = floorIdentifier;
    }

    public List<ParkingSpot> getSpots() {
        return spots;
    }

    public void setSpots(List<ParkingSpot> spots) {
        this.spots = spots;
    }

    @Override
    public String toString() {
        return "Parkovisko - Poschodie: " + id + " | " + carParkId + " | " + floorIdentifier;
    }

}