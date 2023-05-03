package hw4.hw4.Service;

import hw4.hw4.Entity.User.ERole;
import hw4.hw4.Entity.User.Role;
import hw4.hw4.Entity.User.User;
import hw4.hw4.Entity.User.UserProfile;
import hw4.hw4.Exception.UserNotFoundException;
import hw4.hw4.Exception.UserProfileNotFoundException;
import hw4.hw4.Repository.*;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final CarRepository carRepository;

    private final PilotRepository pilotRepository;

    private final RaceRepository raceRepository;

    private final UserProfileRepository userProfileRepository;

    private final RoleRepository roleRepository;

    public UserService(UserRepository userRepository, CarRepository carRepository, PilotRepository pilotRepository, RaceRepository raceRepository, UserProfileRepository userProfileRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.carRepository = carRepository;
        this.pilotRepository = pilotRepository;
        this.raceRepository = raceRepository;
        this.userProfileRepository = userProfileRepository;
        this.roleRepository = roleRepository;
    }

    public UserProfile getUserProfileById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        return user.getUserProfile();
    }

    public UserProfile getUserProfileByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));
        return user.getUserProfile();
    }

    public User getUserByUsername(String username) {
        return this.userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
    }

    public UserProfile updateUserProfile(UserProfile newUserProfile, Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        return userProfileRepository.findById(user.getUserProfile().getId())
                .map(userProfile -> {
                    userProfile.setBio(newUserProfile.getBio());
                    userProfile.setLocation(newUserProfile.getLocation());
                    userProfile.setGender(newUserProfile.getGender());
                    userProfile.setMaritalStatus(newUserProfile.getMaritalStatus());
                    userProfile.setBirthdate(newUserProfile.getBirthdate());
                    return userProfileRepository.save(userProfile);
                })
                .orElseThrow(() -> new UserProfileNotFoundException(id));
    }

    public Integer getUserNumberOfCarsById(Long id) {
        return carRepository.findByUserId(id).size();
    }

    public Integer getUserNumberOfPilotsById(Long id) {
        return pilotRepository.findByUserId(id).size();
    }

    public Integer getUserNumberOfRacesById(Long id) {
        return raceRepository.findByUserId(id).size();
    }

    public List<User> searchUsersByUsername(String username) {
        return this.userRepository.findTop20BySearchTerm(username);
    }

    public User updateRolesUser(HashMap<String, Boolean> roles, Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        Set<Role> roleSet = new HashSet<>();
        if (roles.get("isUser")) {
            Role role = roleRepository.findByName(ERole.ROLE_USER).orElseThrow();//config role exp
            roleSet.add(role);
        }
        if (roles.get("isModerator")){
            Role role = roleRepository.findByName(ERole.ROLE_MODERATOR).orElseThrow();//config role exp
            roleSet.add(role);
        }
        if (roles.get("isAdmin")){
            Role role = roleRepository.findByName(ERole.ROLE_ADMIN).orElseThrow();//config role exp
            roleSet.add(role);
        }
        user.setRoles(roleSet);
        return userRepository.save(user);
    }
}
