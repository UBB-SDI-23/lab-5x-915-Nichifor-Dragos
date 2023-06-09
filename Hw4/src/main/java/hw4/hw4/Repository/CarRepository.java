package hw4.hw4.Repository;

import hw4.hw4.Entity.Car;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import java.util.List;

public interface CarRepository extends JpaRepository<Car, Long> {

    @NonNull
    Page<Car> findByCylindricalCapacityGreaterThan(Pageable pageable, Integer cylindricalCapacity);

    List<Car> findByPilotId(Long id);

    List<Car> findByUserId(Long id);

    @NonNull
    Page<Car> findAll(@NonNull Pageable pageable);

    Long countByCylindricalCapacityGreaterThan(Integer mileageThreshold);

    boolean existsById(Long id);

}