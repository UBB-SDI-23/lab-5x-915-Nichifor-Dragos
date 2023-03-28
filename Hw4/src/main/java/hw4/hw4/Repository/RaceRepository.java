package hw4.hw4.Repository;

import hw4.hw4.Entity.Race;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RaceRepository extends CustomRaceRepository, JpaRepository<Race, Long> {} // interface which extends Spring Data JPA’s