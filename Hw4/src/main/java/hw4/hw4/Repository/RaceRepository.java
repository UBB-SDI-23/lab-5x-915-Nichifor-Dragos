package hw4.hw4.Repository;

import hw4.hw4.Entity.Car;
import hw4.hw4.Entity.Race;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

public interface RaceRepository extends CustomRaceRepository, JpaRepository<Race, Long> {

    @NonNull
    Page<Race> findAll(@NonNull Pageable pageable);

} // interface which extends Spring Data JPA’s