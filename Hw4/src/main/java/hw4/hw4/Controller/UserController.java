package hw4.hw4.Controller;

import hw4.hw4.Entity.Pilot;
import hw4.hw4.Entity.User.User;
import hw4.hw4.Entity.User.UserProfile;
import hw4.hw4.Service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;


@CrossOrigin
@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    UserController(UserService userService) {this.userService = userService;}

    @GetMapping("/user-profile-id/{id}")
    UserProfile getProfileById(@PathVariable Long id) {
        return userService.getUserProfileById(id);
    }

    @GetMapping("/user-profile-username/{username}")
    UserProfile getProfileByUsername(@PathVariable String username) {
        return userService.getUserProfileByUsername(username);
    }

    @GetMapping("/user/{username}")
    User getUserByUsername(@PathVariable String username) {
        return userService.getUserByUsername(username);
    }

    @GetMapping("/user-number-cars/{id}")
    Integer getUserNumberOfCarsById(@PathVariable Long id) {
        return userService.getUserNumberOfCarsById(id);
    }

    @GetMapping("/user-number-pilots/{id}")
    Integer getUserNumberOfPilotsById(@PathVariable Long id) {
        return userService.getUserNumberOfPilotsById(id);
    }

    @GetMapping("/user-number-races/{id}")
    Integer getUserNumberOfRacesById(@PathVariable Long id) {
        return userService.getUserNumberOfRacesById(id);
    }

    @GetMapping("/user-search")
    List<User> getPilotsByName(@RequestParam(required = false) String username) {
        return this.userService.searchUsersByUsername(username);
    }

    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    @PutMapping("/user-profile/{id}")
    UserProfile updateUser(@Valid @RequestBody UserProfile newUserProfile, @PathVariable Long id) {
        return userService.updateUserProfile(newUserProfile, id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/user-roles/{id}")
    User updateUser(@Valid @RequestBody HashMap<String, Boolean> roles, @PathVariable Long id) {
        return userService.updateRolesUser(roles, id);
    }

}
