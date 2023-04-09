package hw4.hw4.Controller;

import hw4.hw4.DTO.Car.CarDTO;
import hw4.hw4.DTO.Car.CarDTO_All;
import hw4.hw4.DTO.Car.CarDTO_One;
import hw4.hw4.Entity.Car;
import hw4.hw4.Service.CarService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
// indicates that the data returned by each method will be written straight into the response body instead of rendering a template.
@RequestMapping("/api")
public class CarController {

    @Autowired
    private ModelMapper modelMapper;
    private final CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping("/car")
        // get all the cars, or filter by cylindrical capacity if there is a parameter specified
    List<CarDTO_All> allCars(@RequestParam(defaultValue = "0") Integer pageNo, @RequestParam(defaultValue = "50") Integer pageSize, @RequestParam(required = false) Integer capacity) {
        if (capacity == null) {
            return this.carService.getAllCars(pageNo, pageSize).stream().map(this::convertToCarDTO_All).collect(Collectors.toList());
        }
        return this.carService.getAllCarsWithCapacityGreaterThan(pageNo, pageSize, capacity).stream().map(this::convertToCarDTO_All).collect(Collectors.toList());
    }

    @GetMapping("/car/{id}")
        // get a car by its id
    CarDTO_One oneCar(@PathVariable Long id) {
        return this.convertToCarDTO_One(this.carService.getOneCar(id));
    }

    @PostMapping("/pilot/{id}/car")
        // add a new car to an existing pilot
    Car newCar(@Valid @RequestBody Car newCar, @PathVariable Long id) {
        return carService.addCar(newCar, id);
    }

    @PostMapping("/pilot/{id}/cars")
        // add a new car to an existing pilot
    List<Car> newCars(@Valid @RequestBody List<Car> newCars, @PathVariable Long id) {
        return carService.addCars(newCars, id);
    }

    @PutMapping("/car/{id}")
        // update a car given the id
    Car replaceCar(@Valid @RequestBody Car newCar, @PathVariable Long id) {
        return carService.updateCar(newCar, id);
    }

    @DeleteMapping("/car/{id}")
        // delete a car given its id
    void deleteCar(@PathVariable Long id) {
        carService.deleteCar(id);
    }

    private CarDTO_All convertToCarDTO_All(Car car) {
        CarDTO_All carDTO = this.modelMapper.map(car, CarDTO_All.class);
        carDTO.setPilotID(car.getPilot().getId());
        return carDTO;
    }

    private CarDTO_One convertToCarDTO_One(Car car) {
        return this.modelMapper.map(car, CarDTO_One.class);
    }

}
