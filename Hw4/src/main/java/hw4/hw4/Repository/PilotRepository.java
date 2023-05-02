package hw4.hw4.Repository;


import hw4.hw4.Entity.Car;
import hw4.hw4.Entity.Pilot;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;

import java.util.List;

public interface PilotRepository  extends CustomPilotRepository ,JpaRepository<Pilot, Long> {

    @NonNull
    Page<Pilot> findAll(@NonNull Pageable pageable);

    @Query(value = "SELECT * FROM pilots " +
            "WHERE to_tsvector('english', last_name) @@ to_tsquery('english', replace(?1, ' ', ':* & ') || ':*') " +
            "OR last_name LIKE ('%' || ?1 || '%') ORDER BY ts_rank(to_tsvector('english', last_name), " +
            "to_tsquery('english', replace(?1, ' ', ':* & ') || ':*')) DESC LIMIT 20", nativeQuery = true)
    List<Pilot> findTop20BySearchTerm(String searchTerm);

    boolean existsById(Long id);

    List<Pilot> findByUserId(Long id);

}
