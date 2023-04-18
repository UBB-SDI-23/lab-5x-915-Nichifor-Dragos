package hw4.hw4.Repository;

import hw4.hw4.Entity.Car;
import hw4.hw4.Entity.RacePilot.RacesPilots;
import hw4.hw4.Entity.RacePilot.RacesPilotsKey;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

public interface RacesPilotsRepository extends JpaRepository<RacesPilots, RacesPilotsKey> {

    Long countByRaceId(Long readerID);

    Long countByPilotId(Long readerID);

} // interface which extends Spring Data JPA’s
