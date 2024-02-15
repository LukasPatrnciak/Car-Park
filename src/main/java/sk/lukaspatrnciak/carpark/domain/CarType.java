package sk.stuba.fei.uim.vsa.pr2.domain;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "\"CAR_TYPE\"")
@NamedQueries({
        @NamedQuery(name = "CarType.findAll", query = "SELECT carType FROM CarType carType"),
        @NamedQuery(name = "CarType.findByName", query = "SELECT carType FROM CarType carType WHERE carType.name = :name"),
        @NamedQuery(name = "CarType.findById", query = "SELECT carType FROM CarType carType WHERE carType.id = :id"),
        @NamedQuery(name = "CarType.findByName", query = "SELECT carType FROM CarType carType WHERE carType.name = :name")
})
public class CarType {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    private String name;

    public CarType(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public CarType() {
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

    @Override
    public String toString() {
        return "T Y P  A U T A: \n\nID typu: " + id + "\nNazov typu: " + name;
    }

}