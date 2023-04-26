package hw4.hw4.Service;

import hw4.hw4.Entity.User.User;
import hw4.hw4.Entity.User.UserProfile;
import hw4.hw4.Exception.RaceNotFoundException;
import hw4.hw4.Exception.UserNotFoundException;
import hw4.hw4.Exception.UserProfileNotFoundException;
import hw4.hw4.Repository.UserProfileRepository;
import hw4.hw4.Repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final UserProfileRepository userProfileRepository;

    public UserService(UserRepository userRepository, UserProfileRepository userProfileRepository) {
        this.userRepository = userRepository;
        this.userProfileRepository = userProfileRepository;
    }

    public UserProfile getUserProfile(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        return user.getUserProfile();
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

}
