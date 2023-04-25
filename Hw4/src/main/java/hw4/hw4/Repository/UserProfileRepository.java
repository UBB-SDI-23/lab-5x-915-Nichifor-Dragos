package hw4.hw4.Repository;

import hw4.hw4.Entity.User.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {}
