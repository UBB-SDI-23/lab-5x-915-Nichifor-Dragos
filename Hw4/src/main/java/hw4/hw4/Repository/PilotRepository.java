package hw4.hw4.Repository;


import hw4.hw4.Entity.Pilot;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PilotRepository  extends CustomPilotRepository ,JpaRepository<Pilot, Long> {} // interface which extends Spring Data JPA’s
