package hw4.hw4.Service;

import hw4.hw4.Entity.Car;
import hw4.hw4.Entity.User.ERole;
import hw4.hw4.Entity.User.User;
import hw4.hw4.Exception.*;
import hw4.hw4.Repository.CarRepository;
import hw4.hw4.Repository.PilotRepository;
import hw4.hw4.Repository.UserRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CarService {

    private final CarRepository carRepository;
    private final PilotRepository pilotRepository;

    private final UserRepository userRepository;

    public CarService(CarRepository carRepository, PilotRepository pilotRepository, UserRepository userRepository)
    {
        this.carRepository = carRepository;
        this.pilotRepository = pilotRepository;
        this.userRepository = userRepository;
    }

    public List<Car> getAllCars(Integer pageNo, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by("id"));

        return this.carRepository.findAll(pageable).getContent();
    }

    public Long getCarsCount() {
        return this.carRepository.count();
    }

    public List<Car> getAllCarsWithCapacityGreaterThan(Integer pageNo, Integer pageSize, Integer capacity) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by("id"));

        return this.carRepository.findByCylindricalCapacityGreaterThan(pageable, capacity).getContent();

    }

    public Long getAllCarsWithCapacityGreaterThanCount(Integer capacity) {
        return this.carRepository.countByCylindricalCapacityGreaterThan(capacity);
    }

    public Car getOneCar(Long id) {
        return carRepository.findById(id)
                .orElseThrow(() -> new CarNotFoundException(id));
    }

    public Car addCar(Car newCar, Long pilotID, Long userID) {
        User user = this.userRepository.findById(userID).orElseThrow(() -> new UserNotFoundException(userID));
        newCar.setUser(user);

        return this.pilotRepository.findById(pilotID)
                .map(pilot -> {
                    newCar.setPilot(pilot);
                    return this.carRepository.save(newCar);
                }).orElseThrow(() -> new PilotNotFoundException(pilotID));
    }

    public List<Car> addCars (List<Car> newCars, Long pilotID, Long userID) {
        User user = this.userRepository.findById(userID).orElseThrow(() -> new UserNotFoundException(userID));

        return this.pilotRepository.findById(pilotID)
                .map(pilot -> {
                    List<Car> newCarsToAdd = new ArrayList<>();
                    Set<Car> pilotCars = pilot.getCars();
                    for (Car newCar : newCars) {
                        boolean add = true;
                        for (Car pilotCar : pilotCars) {
                            if (Objects.equals(pilotCar.getBrand(), newCar.getBrand()) && Objects.equals(pilotCar.getCylindricalCapacity(), newCar.getCylindricalCapacity()) &&
                                    Objects.equals(pilotCar.getMotorization(), newCar.getMotorization()) && Objects.equals(pilotCar.getGearBox(), newCar.getGearBox()) &&
                                    Objects.equals(pilotCar.getHorsePower(), newCar.getHorsePower())) {
                                add = false;
                            }
                        }
                        if (add) {
                            newCar.setPilot(pilot);
                            newCar.setUser(user);
                            newCarsToAdd.add(newCar);
                        }
                    }
                    return this.carRepository.saveAll(newCarsToAdd);
                }).orElseThrow(() -> new PilotNotFoundException(pilotID));
    }

    public Car updateCar(Car newCar, Long carID, Long userID) {
        Car car = this.carRepository.findById(carID).orElseThrow(() -> new RaceNotFoundException(carID));
        User user = this.userRepository.findById(userID).orElseThrow(() -> new UserNotFoundException(userID));

        if (!Objects.equals(user.getId(), car.getUser().getId())) {
            boolean modOrAdmin = user.getRoles().stream().anyMatch((role) ->
                    role.getName() == ERole.ROLE_ADMIN || role.getName() == ERole.ROLE_MODERATOR
            );

            if (!modOrAdmin) {
                throw new UserNotAuthorizedException(String.format("%s does not have permission to " +
                        "update car %s", user.getUsername(), car.getId()));
            }
        }

        return carRepository.findById(carID)
                .map(carUpdate -> {
                    carUpdate.setBrand(newCar.getBrand());
                    carUpdate.setMotorization(newCar.getMotorization());
                    carUpdate.setGearBox(newCar.getGearBox());
                    carUpdate.setCylindricalCapacity(newCar.getCylindricalCapacity());
                    carUpdate.setHorsePower(newCar.getHorsePower());
                    carUpdate.setDescription(newCar.getDescription());
                    return carRepository.save(carUpdate);
                }).orElseThrow(() -> new CarNotFoundException(carID));
    }

    public void deleteCar(Long carID) {
        if(!carRepository.existsById(carID))
            throw new CarNotFoundException(carID);
        carRepository.deleteById(carID);
    }

}
