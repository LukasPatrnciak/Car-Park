package sk.stuba.fei.uim.vsa.pr2.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "\"RESERVATION\"")
@NamedQueries({
        @NamedQuery(name = "Reservation.findBySpotIdDate", query = "SELECT reserv FROM Reservation reserv WHERE reserv.id = :id"),
        @NamedQuery(name = "Reservation.findBySpotId", query = "SELECT reserv FROM Reservation reserv WHERE reserv.parkingSpotId = :idSpot")
        //@NamedQuery(name = "Reservation.findByUserId", query = "SELECT reserv FROM Reservation reserv WHERE reserv.userId = :idUser")
})
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    private Car car;

    private Long parkingSpotId;
    private Long carId;

    private int price;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date startAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date endAt;

    public Reservation(Long id, Car car, Long parkingSpotId, Long carId, int price, Date startAt, Date endAt) {
        this.id = id;
        this.car = car;
        this.parkingSpotId = parkingSpotId;
        this.carId = carId;
        this.price = price;
        this.startAt = startAt;
        this.endAt = endAt;
    }

    public Reservation(Long parkingSpotId, Long carId) {
        this.parkingSpotId = parkingSpotId;
        this.carId = carId;
    }

    public Reservation() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParkingSpotId() {
        return parkingSpotId;
    }

    public void setParkingSpotId(Long parkingSpotId) {
        this.parkingSpotId = parkingSpotId;
    }

    public Long getCarId() {
        return carId;
    }

    public void setCarId(Long cardId) {
        this.carId = cardId;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Date getStartAt() {
        return startAt;
    }

    public void setStartAt(Date startAt) {
        this.startAt = startAt;
    }

    public Date getEndAt() {
        return endAt;
    }

    public void setEndAt(Date endAt) {
        this.endAt = endAt;
    }

    @Override
    public String toString() {
        return "R E Z E R V A C I A: \n\nID: " + id + "\nID Parkovacieho miesta: " + parkingSpotId + "\nID Auta: " + carId;
    }

}