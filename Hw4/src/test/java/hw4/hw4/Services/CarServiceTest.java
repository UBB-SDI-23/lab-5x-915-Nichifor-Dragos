package hw4.hw4.Services;

import hw4.hw4.Entity.Car;
import hw4.hw4.Entity.Pilot;
import hw4.hw4.Repository.CarRepository;
import hw4.hw4.Repository.RaceRepository;
import hw4.hw4.Service.CarService;
import hw4.hw4.Service.RaceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CarServiceTest {

    @Mock
    private CarRepository carRepository;
    @InjectMocks
    private CarService carService;

    @BeforeEach
    void setUp()
    {
        when(carRepository.findByCylindricalCapacityGreaterThan(4000)).thenReturn(Arrays.asList(
                new Car(1L, "Porsche", "benzine", "automatic", 4500, 450, new Pilot()),
                new Car(2L, "Ferrari", "benzine", "automatic", 5500, 590, new Pilot())
        ));
    }

    @Test
    void getAllCarsWithCapacityGreaterThan() {
        List<Car> cars = carService.getAllCarsWithCapacityGreaterThan(4000);
        assertEquals("Porsche", cars.get(0).getBrand());
        assertEquals("Ferrari", cars.get(1).getBrand());
        assertEquals(cars.size(), 2);
    }

}
