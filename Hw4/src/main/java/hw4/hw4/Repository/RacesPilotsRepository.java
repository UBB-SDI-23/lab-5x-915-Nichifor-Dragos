package hw4.hw4.Repository;

import hw4.hw4.Entity.RacePilot.RacesPilots;
import hw4.hw4.Entity.RacePilot.RacesPilotsKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RacesPilotsRepository extends JpaRepository<RacesPilots, RacesPilotsKey> {} // interface which extends Spring Data JPA’s
