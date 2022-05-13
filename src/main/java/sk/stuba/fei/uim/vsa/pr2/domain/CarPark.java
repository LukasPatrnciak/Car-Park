package sk.stuba.fei.uim.vsa.pr2.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "\"CAR_PARK\"")
@NamedQueries({
        @NamedQuery(name = "CarPark.findAll", query = "SELECT cPark FROM CarPark cPark"),
        @NamedQuery(name = "CarPark.findById", query = "SELECT cPark FROM CarPark cPark WHERE cPark.id = :id"),
        @NamedQuery(name = "CarPark.findByName", query = "SELECT cPark FROM CarPark cPark WHERE cPark.name = :name")
})
public class CarPark {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToMany(cascade = CascadeType.PERSIST, orphanRemoval = true)
    @JoinColumn(name = "carParkId")
    private List<CarParkFloor> floors;

    private String name;
    private String address;
    private Integer pricePerHour;

    public CarPark(Long id, String name, String address, Integer pricePerHour) {
        floors = new ArrayList<>();
        this.id = id;
        this.name = name;
        this.address = address;
        this.pricePerHour = pricePerHour;
    }

    public CarPark() {
        floors = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCarParkId() {
        return id;
    }

    public void setCarParkId(Long id) {
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

    public Integer getPricePerHour() {
        return pricePerHour;
    }

    public void setPricePerHour(Integer pricePerHour) {
        this.pricePerHour = pricePerHour;
    }

    public List<CarParkFloor> getFloors() {
        return floors;
    }

    public void setFloors(List<CarParkFloor> floors) {
        this.floors = floors;
    }

    @Override
    public String toString() {
        return "P A R K O V I S K O: \n\nID: " + id + "\nNazov: " + name + "\nAdresa: " + address + "\nCena parkovania: " + pricePerHour + " Eur/Hod";
    }

}