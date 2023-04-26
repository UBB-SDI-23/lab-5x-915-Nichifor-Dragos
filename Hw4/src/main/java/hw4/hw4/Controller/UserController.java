package hw4.hw4.Controller;

import hw4.hw4.Entity.User.UserProfile;
import hw4.hw4.Service.UserService;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;


@CrossOrigin
@RestController
@RequestMapping("/api")
public class UserController {


    private final UserService userService;

    UserController(UserService userService) {this.userService = userService;}

    @GetMapping("/public/user-profile/{id}") // get a user profile by its user id
    UserProfile getProfile(@PathVariable Long id) {
        return userService.getUserProfile(id);
    }

    @PutMapping("/user/user-profile/{id}") // update a user's profile given its user id
    UserProfile updateUser(@Valid @RequestBody UserProfile newUserProfile, @PathVariable Long id) {
        return userService.updateUserProfile(newUserProfile, id);
    }

}
