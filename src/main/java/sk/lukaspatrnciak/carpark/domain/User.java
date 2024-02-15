package sk.stuba.fei.uim.vsa.pr2.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "\"USER\"")
@NamedQueries({
        @NamedQuery(name = "User.findAll", query = "SELECT usr FROM User usr"),
        @NamedQuery(name = "User.findById", query = "SELECT usr FROM User usr WHERE usr.id = :id"),
        @NamedQuery(name = "User.findByEmail", query = "SELECT usr FROM User usr WHERE usr.email = :email")
})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToMany(cascade = CascadeType.PERSIST, orphanRemoval = true)
    @JoinColumn(name = "carId")
    private List<Car> ownedCars;

    private String firstName;
    private String lastName;
    private String email;

    public User(Long id, String firstName, String lastName, String email) {
        ownedCars = new ArrayList<>();
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public User() {
        ownedCars = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Car> getOwnedCars() {
        return ownedCars;
    }

    public void setOwnedCars(List<Car> ownedCars) {
        this.ownedCars = ownedCars;
    }

    @Override
    public String toString() {
        return "P O U Z I V A T E L: \n\nID: " + id + "\nMeno: " + firstName + "\nPriezvisko: " + lastName + "\nEmail: " + email;
    }

}