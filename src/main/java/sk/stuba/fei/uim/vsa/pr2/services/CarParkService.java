package sk.stuba.fei.uim.vsa.pr2.services;

import sk.stuba.fei.uim.vsa.pr2.domain.*;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.*;

/**
 *
 * @author LukasPatrnciak
 */
public class CarParkService extends AbstractCarParkService {

    @Override
    public CarPark createCarPark(Long id, String name, String address, Integer pricePerHour) {
        EntityManager entityManager = emf.createEntityManager();

        try {
            TypedQuery<CarPark> typedQuery = entityManager.createNamedQuery("CarPark.findByName", CarPark.class);
            typedQuery.setParameter("name", name);

            CarPark carPark = typedQuery.getSingleResult();

            return carPark;

        } catch(NoResultException exception) {
            CarPark carPark = new CarPark(id, name, address, pricePerHour);

            entityManager.getTransaction().begin();
            entityManager.persist(carPark);
            entityManager.getTransaction().commit();
            entityManager.close();

            return carPark;
        }
    }

    @Override
    public CarPark getCarPark(Long carParkId) {
        EntityManager entityManager = emf.createEntityManager();

        try {
            TypedQuery<CarPark> typedQuery = entityManager.createNamedQuery("CarPark.findById", CarPark.class);
            typedQuery.setParameter("id", carParkId);

            CarPark carPark = typedQuery.getSingleResult();

            return carPark;

        } catch(NoResultException exception) {
            return null;
        }
    }

    @Override
    public CarPark getCarPark(String carParkName) {
        EntityManager entityManager = emf.createEntityManager();

        try {
            TypedQuery<CarPark> typedQuery = entityManager.createNamedQuery("CarPark.findByName", CarPark.class);
            typedQuery.setParameter("name", carParkName);

            CarPark carPark = typedQuery.getSingleResult();

            return carPark;

        } catch(NoResultException exception) {
            return null;
        }
    }

    @Override
    public List<CarPark> getCarParks() {
        EntityManager entityManager = emf.createEntityManager();

        try {
            TypedQuery<CarPark> typedQuery = entityManager.createNamedQuery("CarPark.findAll", CarPark.class);

            List<CarPark> carParks = typedQuery.getResultList();

            return carParks;

        } catch(NoResultException exception) {
            return null;
        }
    }

    @Override
    public CarPark updateCarPark(Object carPark) {
        EntityManager entityManager = emf.createEntityManager();

        CarPark update = (CarPark)carPark;

        CarPark cPark = entityManager.find(CarPark.class, update.getCarParkId());

        if(update.getName() != null) cPark.setName(update.getName());
        if(update.getPricePerHour() != null) cPark.setPricePerHour(update.getPricePerHour());
        if(update.getAddress() != null) cPark.setAddress(update.getAddress());

        entityManager.getTransaction().begin();
        entityManager.persist(cPark);
        entityManager.getTransaction().commit();
        entityManager.close();

        return cPark;
    }

    @Override
    public CarPark deleteCarPark(Long carParkId) {
        EntityManager entityManager = emf.createEntityManager();

        TypedQuery<CarPark> typedQuery = entityManager.createNamedQuery("CarPark.findById", CarPark.class);
        typedQuery.setParameter("id", carParkId);

        CarPark carPark = typedQuery.getSingleResult();

        if(carPark == null) {
            return null;
        }

        for(CarParkFloor carParkFloor: carPark.getFloors()) {
            if(deleteCarParkFloor(carParkId, carParkFloor.getFloorIdentifier()) == null) {
                throw new RuntimeException("Chyba pri odstranovani parkoviska");
            }
        }

        entityManager.getTransaction().begin();
        entityManager.remove(carPark);
        entityManager.getTransaction().commit();

        return carPark;
    }

    @Override
    public CarParkFloor createCarParkFloor(Long carParkId, String floorIdentifier) {
        EntityManager entityManager = emf.createEntityManager();

        CarParkFloor carParkFloor = new CarParkFloor(carParkId, floorIdentifier);
        CarPark carPark = entityManager.find(CarPark.class, carParkId);

        entityManager.getTransaction().begin();

        try {
            carPark.getFloors().add(carParkFloor);
            entityManager.getTransaction().commit();

        } catch(NoResultException exception) {
            System.err.println("\nChyba pri pridavani poschodia do parkovacieho domu");
            exception.printStackTrace();
            entityManager.getTransaction().rollback();
            entityManager.close();

            return null;
        }

        return carParkFloor;
    }

    public CarParkFloor getCarParkFloor(String identificator) {
        EntityManager entityManager = emf.createEntityManager();

        try {
            CarParkFloor carParkFloor = entityManager.createQuery("SELECT CarParkFloor FROM CarParkFloor carParkFloor WHERE carParkFloor.floorIdentifier = :idf", CarParkFloor.class).setParameter("idf", identificator).getSingleResult();

            return carParkFloor;

        } catch(NoResultException exception) {
            return null;
        }
    }

    @Override
    public CarParkFloor getCarParkFloor(Long carParkFloorId) {
        EntityManager entityManager = emf.createEntityManager();

        try {
            TypedQuery<CarParkFloor> typedQuery = entityManager.createNamedQuery("CarPark.findById", CarParkFloor.class);
            typedQuery.setParameter("id", carParkFloorId);

            CarParkFloor carParkFloor = typedQuery.getSingleResult();

            return carParkFloor;

        } catch(NoResultException exception) {
            return null;
        }
    }

    @Override
    public List<CarParkFloor> getCarParkFloors(Long carParkId) {
        EntityManager entityManager = emf.createEntityManager();

        try {
            TypedQuery<CarParkFloor> typedQuery = entityManager.createNamedQuery("CarParkFloor.findAll", CarParkFloor.class);
            typedQuery.setParameter("id", carParkId);

            List<CarParkFloor> carParkFloors = typedQuery.getResultList();

            return carParkFloors;

        } catch(NoResultException exception) {
            return null;
        }
    }

    @Override
    public CarParkFloor updateCarParkFloor(Object carParkFloor) {
        EntityManager entityManager = emf.createEntityManager();

        CarParkFloor update = (CarParkFloor)carParkFloor;

        CarParkFloor cParkFloor = entityManager.find(CarParkFloor.class, update.getId());

        if(update.getFloorIdentifier() != null) cParkFloor.setFloorIdentifier(update.getFloorIdentifier());

        entityManager.getTransaction().begin();
        entityManager.persist(cParkFloor);
        entityManager.getTransaction().commit();
        entityManager.close();

        return cParkFloor;
    }

    @Override
    public CarParkFloor deleteCarParkFloor(Long carParkId, String floorIdentifier) {
        EntityManager entityManager = emf.createEntityManager();

        try {
            TypedQuery<CarParkFloor> typedQuery = entityManager.createNamedQuery("CarParkFloor.findByIdAndFloor", CarParkFloor.class);
            typedQuery.setParameter("id", carParkId);
            typedQuery.setParameter("floor", floorIdentifier);

            CarParkFloor carParkFloor = typedQuery.getSingleResult();

            entityManager.getTransaction().begin();
            entityManager.remove(carParkFloor);
            entityManager.getTransaction().commit();
            entityManager.close();

            return carParkFloor;

        } catch(NoResultException exception) {
            return null;
        }
    }

    @Override
    public CarParkFloor deleteCarParkFloor(Long carParkFloorId) {
        EntityManager entityManager = emf.createEntityManager();

        try {
            TypedQuery<CarParkFloor> typedQuery = entityManager.createNamedQuery("CarParkFloor.findById", CarParkFloor.class);
            typedQuery.setParameter("id", carParkFloorId);

            CarParkFloor carParkFloor = typedQuery.getSingleResult();

            entityManager.getTransaction().begin();
            entityManager.remove(carParkFloor);
            entityManager.getTransaction().commit();
            entityManager.close();

            return carParkFloor;

        } catch(NoResultException exception) {
            return null;
        }
    }

    @Override
    public ParkingSpot createParkingSpot(Long carParkId, String floorIdentifier, String spotIdentifier) {
        EntityManager entityManager = emf.createEntityManager();

        CarPark carPark = entityManager.find(CarPark.class, carParkId);
        if(carPark == null) {
            return null;
        }

        TypedQuery<CarParkFloor> typedQuery = entityManager.createNamedQuery("CarParkFloor.findByFloor", CarParkFloor.class);
        typedQuery.setParameter("floor", floorIdentifier);

        CarParkFloor carParkFloor = typedQuery.getSingleResult();

        if(carParkFloor == null) {
            return null;
        }

        ParkingSpot parkingSpot = new ParkingSpot(carParkId, spotIdentifier, null, carPark.getPricePerHour());

        entityManager.getTransaction().begin();
        entityManager.persist(parkingSpot);
        entityManager.getTransaction().commit();

        return parkingSpot;
    }

    @Override
    public ParkingSpot getParkingSpot(Long parkingSpotId) {
        EntityManager entityManager = emf.createEntityManager();

        try {
            TypedQuery<ParkingSpot> typedQuery = entityManager.createNamedQuery("ParkingSpot.findById", ParkingSpot.class);
            typedQuery.setParameter("id", parkingSpotId);

            ParkingSpot carParkingSpot = typedQuery.getSingleResult();

            return carParkingSpot;

        } catch(NoResultException exception) {
            return null;
        }
    }

    @Override
    public List<ParkingSpot> getParkingSpots(Long carParkId, String floorIdentifier) {
        EntityManager entityManager = emf.createEntityManager();

        try {
            TypedQuery<ParkingSpot> typedQuery = entityManager.createNamedQuery("ParkingSpot.findByIdAndFloor", ParkingSpot.class);
            typedQuery.setParameter("id", carParkId);
            typedQuery.setParameter("floor", floorIdentifier);

            List<ParkingSpot> parkingSpot = typedQuery.getResultList();

            return parkingSpot;

        } catch(NoResultException exception) {
            return null;
        }
    }

    @Override
    public Map<String, List<ParkingSpot>> getParkingSpots(Long carParkId) {
        EntityManager entityManager = emf.createEntityManager();
        HashMap<String, List<ParkingSpot>> parkingSpotsMap = new HashMap<>();

        TypedQuery<CarPark> typedQuery = entityManager.createNamedQuery("CarPark.findById", CarPark.class);
        typedQuery.setParameter("id", carParkId);

        CarPark carPark = typedQuery.getSingleResult();

        if(carPark == null) {
            return null;
        }

        for(CarParkFloor carParkFloor: carPark.getFloors()) {
            parkingSpotsMap.put(carParkFloor.getFloorIdentifier(), carParkFloor.getSpots());
        }

        return parkingSpotsMap;
    }

    @Override
    public Map<String, List<ParkingSpot>> getAvailableParkingSpots(String carParkName) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Map<String, List<ParkingSpot>> getOccupiedParkingSpots(String carParkName) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ParkingSpot updateParkingSpot(ParkingSpot parkingSpot) {
        EntityManager entityManager = emf.createEntityManager();

        if(entityManager.find(ParkingSpot.class, parkingSpot.getId()) != null) {
            entityManager.getTransaction().begin();
            entityManager.persist(parkingSpot);
            entityManager.getTransaction().commit();
            entityManager.close();

            return parkingSpot;
        }

        return null;
    }

    @Override
    public ParkingSpot deleteParkingSpot(Long parkingSpotId) {
        EntityManager entityManager = emf.createEntityManager();

        ParkingSpot parkingSpot = entityManager.find(ParkingSpot.class, parkingSpotId);

        if(parkingSpot == null) {
            return null;
        }

        entityManager.getTransaction().begin();
        entityManager.remove(parkingSpot);
        entityManager.getTransaction().commit();

        return parkingSpot;
    }

    @Override
    public Car createCar(Long id, Long userId, String brand, String model, String colour, String vehicleRegistrationPlate) {
        EntityManager entityManager = emf.createEntityManager();

        try {
            TypedQuery<Car> typedQuery = entityManager.createNamedQuery("Car.findByPlate", Car.class);
            typedQuery.setParameter("plate", vehicleRegistrationPlate);

            Car car = typedQuery.getSingleResult();

            return car;

        } catch(NoResultException exception) {
            User owner = entityManager.find(User.class, userId);

            if(owner == null || vehicleRegistrationPlate.isEmpty()) {
                return null;
            }

            Car car = new Car(null, owner.getId(), brand, model, colour, vehicleRegistrationPlate);

            if(getUser(owner.getId()) == null) {
                return null;
            }

            owner.getOwnedCars().add(car);

            entityManager.getTransaction().begin();
            entityManager.persist(owner);
            entityManager.persist(car);
            entityManager.getTransaction().commit();
            entityManager.close();

            return car;
        }
    }

    @Override
    public List<Car> getCars() {
        EntityManager entityManager = emf.createEntityManager();

        try {
            TypedQuery<Car> typedQuery = entityManager.createNamedQuery("Car.findAll", Car.class);
            List<Car> carsList = typedQuery.getResultList();

            return carsList;

        } catch(NoResultException exception) {
            return null;
        }
    }

    @Override
    public Car getCar(Long carId) {
        EntityManager entityManager = emf.createEntityManager();

        TypedQuery<Car> typedQuery = entityManager.createNamedQuery("Car.findById", Car.class);
        typedQuery.setParameter("id", carId);

        Car car = typedQuery.getSingleResult();

        return car;
    }

    @Override
    public Car getCar(String vehicleRegistrationPlate) {
        EntityManager entityManager = emf.createEntityManager();

        TypedQuery<Car> typedQuery = entityManager.createNamedQuery("Car.findByPlate", Car.class);
        typedQuery.setParameter("plate", vehicleRegistrationPlate);

        Car car = typedQuery.getSingleResult();

        return car;
    }

    @Override
    public List<Car> getCars(Long userId) {
        EntityManager entityManager = emf.createEntityManager();

        TypedQuery<Car> typedQuery = entityManager.createNamedQuery("Car.findByUserId", Car.class);
        List<Car> carsList = typedQuery.getResultList();

        return carsList;
    }

    @Override
    public Car updateCar(Car car) {
        EntityManager entityManager = emf.createEntityManager();

        if (entityManager.find(Car.class, car.getId()) != null) {
            entityManager.getTransaction().begin();
            entityManager.persist(car);
            entityManager.getTransaction().commit();
            entityManager.close();

            return car;
        }

        return null;
    }

    @Override
    public Car deleteCar(Long carId) {
        EntityManager entityManager = emf.createEntityManager();

        Car car = entityManager.find(Car.class, carId);

        if(car == null) {
            return null;
        }

        entityManager.getTransaction().begin();
        entityManager.remove(car);
        entityManager.getTransaction().commit();

        return car;
    }

    @Override
    public User createUser(Long id, String firstname, String lastname, String email) {
        EntityManager entityManager = emf.createEntityManager();

        try {
            TypedQuery<User> typedQuery = entityManager.createNamedQuery("User.findByEmail", User.class);
            typedQuery.setParameter("email", email);

            User user = typedQuery.getSingleResult();

            return user;

        } catch(NoResultException exception) {
            User user = new User(id, firstname, lastname, email);

            entityManager.getTransaction().begin();
            entityManager.persist(user);
            entityManager.getTransaction().commit();
            entityManager.close();

            return user;
        }
    }

    @Override
    public User getUser(Long userId) {
        EntityManager entityManager = emf.createEntityManager();

        try {
            TypedQuery<User> typedQuery = entityManager.createNamedQuery("User.findById", User.class);
            typedQuery.setParameter("id", userId);

            User user = typedQuery.getSingleResult();

            return user;

        } catch(NoResultException exception) {
            return null;
        }
    }

    @Override
    public User getUser(String email) {
        EntityManager entityManager = emf.createEntityManager();

        try {
            TypedQuery<User> typedQuery = entityManager.createNamedQuery("User.findByEmail", User.class);
            typedQuery.setParameter("email", email);

            User user = typedQuery.getSingleResult();

            return user;

        } catch(NoResultException exception) {
            return null;
        }
    }

    @Override
    public List<User> getUsers() {
        EntityManager entityManager = emf.createEntityManager();

        TypedQuery<User> typedQuery = entityManager.createNamedQuery("User.findAll", User.class);
        List<User> users = typedQuery.getResultList();
        List<User> resultList = new ArrayList<>(users);

        return resultList;
    }

    @Override
    public User updateUser(User user) {
        EntityManager entityManager = emf.createEntityManager();

        User newUser = (User) user;
        User updateUser = entityManager.find(User.class, newUser.getId());

        if(updateUser == null) {
            return createUser(newUser.getId(), newUser.getFirstName(), newUser.getLastName(), newUser.getEmail());
        }

        updateUser.setFirstName(newUser.getFirstName());
        updateUser.setLastName(newUser.getLastName());
        updateUser.setEmail(newUser.getEmail());
        updateUser.setOwnedCars(newUser.getOwnedCars());

        return updateUser;
    }

    @Override
    public User deleteUser(Long userId) {
        EntityManager entityManager = emf.createEntityManager();

        if(userId == null) {
            return null;
        }

        TypedQuery<User> typedQuery = entityManager.createNamedQuery("User.findById", User.class);
        typedQuery.setParameter("id", userId);

        User user = typedQuery.getSingleResult();

        entityManager.getTransaction().begin();
        entityManager.remove(user);
        entityManager.getTransaction().commit();

        return user;
    }

    @Override
    public Reservation createReservation(Long id, Long parkingSpotId, Long carId) {
        EntityManager entityManager = emf.createEntityManager();

        ParkingSpot parkingSpot = entityManager.find(ParkingSpot.class, parkingSpotId);
        Car car = entityManager.find(Car.class, carId);

        if(parkingSpot == null || car == null) {
            return null;
        }

        Reservation reservation = new Reservation(parkingSpotId, carId);

        entityManager.getTransaction().begin();
        entityManager.persist(reservation);
        entityManager.getTransaction().commit();

        return reservation;
    }

    @Override
    public Reservation getReservation(Long reservationId) {
        EntityManager entityManager = emf.createEntityManager();

        try {
            TypedQuery<Reservation> typedQuery = entityManager.createNamedQuery("Reservation.findBySpotId", Reservation.class);
            typedQuery.setParameter("id", reservationId);

            Reservation reservation = typedQuery.getSingleResult();

            return reservation;

        } catch(NoResultException exception) {
            return null;
        }
    }

    @Override
    public Reservation endReservation(Long reservationId) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Reservation> getReservations(Long parkingSpotId, Date date) {
        EntityManager entityManager = emf.createEntityManager();
        double time = 86400000.0;

        ParkingSpot parkingSpot = entityManager.find(ParkingSpot.class, parkingSpotId);

        if(parkingSpot == null) {
            return null;
        }

        TypedQuery<Reservation> typedQuery = entityManager.createNamedQuery("Reservation.findBySpotIdDate", Reservation.class);
        typedQuery.setParameter("id", parkingSpotId);

        List<Reservation> reservationList = typedQuery.getResultList();
        List<Reservation> reservationsDate = new ArrayList<>();

        for(Reservation reservation: reservationList) {
            if(date.before(reservation.getStartAt()) && (reservation.getStartAt().getTime() - date.getTime() < time)) {
                reservationsDate.add(reservation);
            }
        }

        return reservationsDate;
    }

    @Override
    public List<Reservation> getMyReservations(Long userId) {
        EntityManager entityManager = emf.createEntityManager();

        User user = entityManager.find(User.class, userId);

        if(user == null) {
            return null;
        }

        TypedQuery<Reservation> typedQuery = entityManager.createNamedQuery("Reservation.findByUserId", Reservation.class);
        typedQuery.setParameter("idUser", userId);

        List<Reservation> reservationList = typedQuery.getResultList();

        return reservationList;
    }

    @Override
    public Reservation updateReservation(Object reservation) {
        EntityManager entityManager = emf.createEntityManager();

        Reservation newReservation = (Reservation) reservation;
        Reservation updateReservation = entityManager.find(Reservation.class, newReservation.getId());

        updateReservation.setCarId(newReservation.getCarId());
        updateReservation.setParkingSpotId(newReservation.getParkingSpotId());

        updateReservation.setPrice(newReservation.getPrice());

        updateReservation.setStartAt(newReservation.getStartAt());
        updateReservation.setEndAt(newReservation.getEndAt());

        entityManager.getTransaction().begin();
        entityManager.persist(updateReservation);
        entityManager.getTransaction().commit();

        return updateReservation;
    }

    @Override
    public CarType createCarType(Long id, String name) {
        EntityManager entityManager = emf.createEntityManager();

        try {
            TypedQuery<CarType> typedQuery = entityManager.createNamedQuery("CarType.findByName", CarType.class);
            typedQuery.setParameter("name", name);

            CarType carTypeName = typedQuery.getSingleResult();

            return carTypeName;

        } catch(NoResultException exception) {
            CarType carType = new CarType(id, name);

            entityManager.getTransaction().begin();
            entityManager.persist(carType);
            entityManager.getTransaction().commit();
            entityManager.close();

            return carType;
        }
    }

    @Override
    public List<CarType> getCarTypes() {
        EntityManager entityManager = emf.createEntityManager();

        TypedQuery<CarType> typedQuery = entityManager.createNamedQuery("CarType.findAll", CarType.class);

        List<CarType> carTypes = typedQuery.getResultList();

        return carTypes;
    }

    @Override
    public CarType getCarType(Long carTypeId) {
        EntityManager entityManager = emf.createEntityManager();

        TypedQuery<CarType> typedQuery = entityManager.createNamedQuery("CarType.findById", CarType.class);
        typedQuery.setParameter("id", carTypeId);

        CarType carType = typedQuery.getSingleResult();

        return carType;
    }

    @Override
    public CarType getCarType(String name) {
        EntityManager entityManager = emf.createEntityManager();

        TypedQuery<CarType> typedQuery = entityManager.createNamedQuery("CarType.findByName", CarType.class);
        typedQuery.setParameter("name", name);

        CarType carType = typedQuery.getSingleResult();

        return carType;
    }

    @Override
    public CarType deleteCarType(Long carTypeId) {
        EntityManager entityManager = emf.createEntityManager();

        TypedQuery<CarType> typedQuery = entityManager.createNamedQuery("CarType.findById", CarType.class);
        typedQuery.setParameter("id", carTypeId);

        CarType carType = typedQuery.getSingleResult();

        if(carType == null) {
            return null;
        }

        entityManager.getTransaction().begin();
        entityManager.remove(carType);
        entityManager.getTransaction().commit();

        return carType;
    }

    @Override
    public Car createCar(Long id, Long userId, String brand, String model, String colour, String vehicleRegistrationPlate, Long carTypeId) {
        EntityManager entityManager = emf.createEntityManager();

        try {
            TypedQuery<Car> typedQuery = entityManager.createNamedQuery("Car.findByPlate", Car.class);
            typedQuery.setParameter("plate", vehicleRegistrationPlate);

            Car car = typedQuery.getSingleResult();

            return car;

        } catch(NoResultException exception) {
            User owner = entityManager.find(User.class, userId);

            if(owner == null || vehicleRegistrationPlate.isEmpty() || carTypeId == null) {
                return null;
            }

            Car car = new Car(id, owner.getId(), brand, model, colour, vehicleRegistrationPlate, carTypeId);

            if(getUser(owner.getId()) == null) {
                return null;
            }

            owner.getOwnedCars().add(car);

            entityManager.getTransaction().begin();
            entityManager.persist(owner);
            entityManager.persist(car);
            entityManager.getTransaction().commit();
            entityManager.close();

            return car;
        }
    }

    @Override
    public ParkingSpot createParkingSpot(Long carParkId, String floorIdentifier, String spotIdentifier, Long carTypeId) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void persist(Object object) {
        EntityManager entityManager = emf.createEntityManager();
        entityManager.getTransaction().begin();

        try {
            entityManager.persist(object);
            entityManager.getTransaction().commit();

        } catch (Exception exception) {
            entityManager.getTransaction().rollback();

        } finally {
            entityManager.close();
        }
    }

}