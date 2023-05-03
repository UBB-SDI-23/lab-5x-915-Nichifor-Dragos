package hw4.hw4.Controller;

import hw4.hw4.DTO.Car.CarDTO_All;
import hw4.hw4.DTO.Car.CarDTO_One;
import hw4.hw4.Entity.Car;
import hw4.hw4.Entity.User.User;
import hw4.hw4.Security.JWT.JwtUtils;
import hw4.hw4.Service.CarService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import hw4.hw4.Service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = {"http://localhost:4200", "https://racemasters.netlify.app"}, allowCredentials = "true")
@RestController
@RequestMapping("/api")
@Validated
public class CarController {

    @Autowired
    private ModelMapper modelMapper;

    private final CarService carService;

    private final UserService userService;

    private final JwtUtils jwtUtils;

    public CarController(CarService carService, UserService userService, JwtUtils jwtUtils) {
        this.carService = carService;
        this.userService = userService;
        this.jwtUtils = jwtUtils;
    }

    @GetMapping("/car")
    List<CarDTO_All> allCars(@RequestParam(defaultValue = "0") Integer pageNo,
                             @RequestParam(defaultValue = "50") Integer pageSize,
                             @RequestParam(required = false) Integer capacity) {
        if (capacity == null) {
            return this.carService.getAllCars(pageNo, pageSize).stream().map(this::convertToCarDTO_All).collect(Collectors.toList());
        }
        return this.carService.getAllCarsWithCapacityGreaterThan(pageNo, pageSize, capacity).stream().map(this::convertToCarDTO_All).collect(Collectors.toList());
    }

    @GetMapping("/car/count")
    Long getCarsCount() {
        return this.carService.getCarsCount();
    }

    @GetMapping("/car/count-capacity")
    Long getAllCarsWithCapacityGreaterThanCount(@RequestParam Integer capacity) {
        return this.carService.getAllCarsWithCapacityGreaterThanCount(capacity);
    }

    @GetMapping("/car/{id}")
    CarDTO_One oneCar(@PathVariable Long id) {
        return this.convertToCarDTO_One(this.carService.getOneCar(id));
    }

    @PostMapping("/pilot/{id}/car")
    Car newCar(@Valid @RequestBody Car newCar,
               @PathVariable Long id,
               @RequestHeader("Authorization") String token) {
        String username = this.jwtUtils.getUserNameFromJwtToken(token);
        User user = this.userService.getUserByUsername(username);

        return carService.addCar(newCar, id, user.getId());
    }

    @PostMapping("/pilot/{id}/cars")
    List<Car> newCars(@Valid @RequestBody List<Car> newCars,
                      @PathVariable Long id,
                      @RequestHeader("Authorization") String token) {
        String username = this.jwtUtils.getUserNameFromJwtToken(token);
        User user = this.userService.getUserByUsername(username);

        return carService.addCars(newCars, id, user.getId());
    }

    @PutMapping("/car/{id}")
    Car replaceCar(@Valid @RequestBody Car newCar,
                   @PathVariable Long id,
                   @RequestHeader("Authorization") String token) {
        String username = this.jwtUtils.getUserNameFromJwtToken(token);
        User user = this.userService.getUserByUsername(username);

        return carService.updateCar(newCar, id, user.getId());
    }

    @DeleteMapping("/car/{id}")
    void deleteCar(@PathVariable Long id,
                   @RequestHeader("Authorization") String token) {
        String username = this.jwtUtils.getUserNameFromJwtToken(token);
        User user = this.userService.getUserByUsername(username);

        carService.deleteCar(id,user.getId());
    }

    private CarDTO_All convertToCarDTO_All(Car car) {
        CarDTO_All carDTO = this.modelMapper.map(car, CarDTO_All.class);
        carDTO.setPilotID(car.getPilot().getId());
        carDTO.setUsername(car.getUser().getUsername());
        return carDTO;
    }

    private CarDTO_One convertToCarDTO_One(Car car) {
        CarDTO_One carDTO = this.modelMapper.map(car, CarDTO_One.class);
        carDTO.setUsername(car.getUser().getUsername());
        return carDTO;
    }

}
