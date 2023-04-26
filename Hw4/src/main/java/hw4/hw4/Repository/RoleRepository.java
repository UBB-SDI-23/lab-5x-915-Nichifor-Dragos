package hw4.hw4.Repository;

import java.util.Optional;

import hw4.hw4.Entity.User.ERole;
import hw4.hw4.Entity.User.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(ERole name);

}