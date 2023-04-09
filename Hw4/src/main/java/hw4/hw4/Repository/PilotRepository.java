package hw4.hw4.Repository;


import hw4.hw4.Entity.Car;
import hw4.hw4.Entity.Pilot;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

public interface PilotRepository  extends CustomPilotRepository ,JpaRepository<Pilot, Long> {

    @NonNull
    Page<Pilot> findAll(@NonNull Pageable pageable);

} // interface which extends Spring Data JPA’s
