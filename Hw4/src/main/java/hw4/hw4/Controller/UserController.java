package hw4.hw4.Controller;

import hw4.hw4.DTO.SQL.SQLRunResponseDTO;
import hw4.hw4.Entity.Config.WebsiteSettings;
import hw4.hw4.Entity.User.User;
import hw4.hw4.Entity.User.UserProfile;
import hw4.hw4.Exception.RaceNotFoundException;
import hw4.hw4.Repository.WebsiteSettingsRepository;
import hw4.hw4.Security.JWT.JwtUtils;
import hw4.hw4.Service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;


@CrossOrigin(allowCredentials = "true", origins = {"http://localhost:4200", "https://racemasters.netlify.app"})
@RestController
@RequestMapping("/api")
@Validated
public class UserController {

    private final UserService userService;

    private final JwtUtils jwtUtils;

    private final WebsiteSettingsRepository websiteSettingsRepository;

    UserController(UserService userService, JwtUtils jwtUtils, WebsiteSettingsRepository websiteSettingsRepository) {
        this.userService = userService;
        this.jwtUtils = jwtUtils;
        this.websiteSettingsRepository = websiteSettingsRepository;
    }

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

    @PutMapping("/user-profile/{id}")
    UserProfile updateUserProfile(@Valid @RequestBody UserProfile newUserProfile,
                           @PathVariable Long id) {

        return userService.updateUserProfile(newUserProfile, id);
    }

    @PutMapping("/user-roles/{id}")
    User updateUser(@Valid @RequestBody HashMap<String, Boolean> roles,
                    @PathVariable Long id,
                    @RequestHeader("Authorization") String token) {
        String username = this.jwtUtils.getUserNameFromJwtToken(token);
        User user = this.userService.getUserByUsername(username);

        return userService.updateRolesUser(roles, id, user.getId());
    }

    @GetMapping("get-entities-per-page")
    Integer getPageSize() {
        return this.websiteSettingsRepository.getById(1L).getEntitiesPerPage();
    }

    @PostMapping("/modify-entities-per-page")
    ResponseEntity<?> setElementsPerPage(@RequestBody WebsiteSettings settings) {
        WebsiteSettings userSettings = this.websiteSettingsRepository.getById(1L);
        userSettings.setEntitiesPerPage(settings.getEntitiesPerPage());
        this.websiteSettingsRepository.save(userSettings);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new SQLRunResponseDTO("Successfully updated the number of elements per page"));
    }

}
