package sk.stuba.fei.uim.vsa.pr2.web.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import sk.stuba.fei.uim.vsa.pr2.domain.Car;
import sk.stuba.fei.uim.vsa.pr2.domain.ParkingSpot;

import java.util.Date;

public class ReservationDto {

    private Long id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date start;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date end;
    private Number prices;
    private CarDto car;
    private ParkingSpotDto spot;

    public ReservationDto(Long id, Date start, Date end, Number prices, CarDto car, ParkingSpotDto spot) {
        this.id = id;
        this.start = start;
        this.end = end;
        this.prices = prices;
        this.car = car;
        this.spot = spot;
    }

    public ReservationDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public Number getPrices() {
        return prices;
    }

    public void setPrices(Number prices) {
        this.prices = prices;
    }

    public CarDto getCar() {
        return car;
    }

    public void setCar(CarDto car) {
        this.car = car;
    }

    public ParkingSpotDto getSpot() {
        return spot;
    }

    public void setSpot(ParkingSpotDto spot) {
        this.spot = spot;
    }

}
