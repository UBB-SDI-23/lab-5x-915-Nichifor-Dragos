package hw4.hw4.Repository;

import hw4.hw4.Entity.User.UserJwt;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJwtRepository extends JpaRepository<UserJwt, Long> {

    Boolean existsByJwtToken(String jwtToken);

    Boolean existsByUsername(String username);

    UserJwt findByJwtToken(String jwtToken);

    void deleteByUsername(String username);

}
