package hw4.hw4.Service;

import hw4.hw4.Entity.Car;
import hw4.hw4.Entity.Pilot;
import hw4.hw4.Exception.CarNotFoundException;
import hw4.hw4.Exception.PilotNotFoundException;
import hw4.hw4.Repository.CarRepository;
import hw4.hw4.Repository.PilotRepository;
import jakarta.validation.Valid;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CarService {

    private final CarRepository carRepository;
    private final PilotRepository pilotRepository;

    public CarService(CarRepository carRepository, PilotRepository pilotRepository)
    {
        this.carRepository = carRepository;
        this.pilotRepository = pilotRepository;
    }

    public List<Car> getAllCars(Integer pageNo, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by("id"));

        return this.carRepository.findAll(pageable).getContent();
    }

    public Long getCarsCount() {
        return this.carRepository.count();
    }

    public Long getCarsCapacityCount(Integer capacity) {
        return this.carRepository.countByCylindricalCapacityGreaterThan(capacity);
    }

    public Car getOneCar(Long id) {
        return carRepository.findById(id)
                .orElseThrow(() -> new CarNotFoundException(id));
    }

    public List<Car> getAllCarsWithCapacityGreaterThan(Integer pageNo, Integer pageSize, Integer capacity) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by("id"));

        return this.carRepository.findByCylindricalCapacityGreaterThan(pageable, capacity).getContent();

    }

    public Car addCar(Car newCar, Long id) {
        return this.pilotRepository.findById(id)
                .map(pilot -> {
                    newCar.setPilot(pilot);
                    return this.carRepository.save(newCar);
                }).orElseThrow(() -> new PilotNotFoundException(id));
    }

    public List<Car> addCars (List<Car> newCars, Long id) {
        return this.pilotRepository.findById(id)
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
                            newCarsToAdd.add(newCar);
                        }
                    }
                    return this.carRepository.saveAll(newCarsToAdd);
                }).orElseThrow(() -> new PilotNotFoundException(id));
    }

    public Car updateCar(Car newCar, Long id) {
        return carRepository.findById(id)
                .map(car -> {
                    car.setBrand(newCar.getBrand());
                    car.setMotorization(newCar.getMotorization());
                    car.setGearBox(newCar.getGearBox());
                    car.setCylindricalCapacity(newCar.getCylindricalCapacity());
                    car.setHorsePower(newCar.getHorsePower());
                    return carRepository.save(car);
                })
                .orElseGet(() -> {
                    newCar.setId(id);
                    return carRepository.save(newCar);
                });
    }

    public void deleteCar(Long id) {
        carRepository.deleteById(id);
    }

}
