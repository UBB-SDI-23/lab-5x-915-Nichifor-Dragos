package hw4.hw4.Controller;

import hw4.hw4.DTO.RacesPilotsDTO.RacesPilotsDTO_One;
import hw4.hw4.Entity.RacePilot.RacesPilots;
import hw4.hw4.Entity.User.User;
import hw4.hw4.Security.JWT.JwtUtils;
import hw4.hw4.Service.RacesPilotsService;
import hw4.hw4.Service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@CrossOrigin(allowCredentials = "true", origins = {"http://localhost:4200", "https://racemasters.netlify.app"})
@RestController
@RequestMapping("/api")
@Validated
public class RacesPilotsController {

    private final RacesPilotsService racesPilotsService;

    private final UserService userService;

    private final JwtUtils jwtUtils;

    RacesPilotsController (RacesPilotsService racesPilotsService, UserService userService, JwtUtils jwtUtils) {
        this.racesPilotsService = racesPilotsService;
        this.userService = userService;
        this.jwtUtils = jwtUtils;
    }

    @GetMapping("/races/pilots")
    List<RacesPilots> allRacesPilots() {
        return this.racesPilotsService.getAllRacesPilots();
    }

    @GetMapping("/races/{idRace}/pilots/{idPilot}")
    RacesPilotsDTO_One oneRacesPilots(@PathVariable Long idRace,
                                      @PathVariable Long idPilot) {
        return this.racesPilotsService.getOneRacesPilots(idRace, idPilot);
    }

    @PostMapping("/races/{idRace}/pilots/{idPilot}")
    RacesPilots newRacesPilots(@RequestBody RacesPilots newRacesPilots,
                               @PathVariable Long idRace, @PathVariable Long idPilot,
                               @RequestHeader("Authorization") String token) {
        String username = this.jwtUtils.getUserNameFromJwtToken(token);
        User user = this.userService.getUserByUsername(username);

        return racesPilotsService.addRacesPilots(newRacesPilots, idRace, idPilot, user.getId());
    }

    @PutMapping("/races/{idRace}/pilots/{idPilot}")
    RacesPilots replaceRacesPilots(@RequestBody RacesPilots newRacesPilots,
                                   @PathVariable Long idRace,
                                   @PathVariable Long idPilot,
                                   @RequestHeader("Authorization") String token) {
        String username = this.jwtUtils.getUserNameFromJwtToken(token);
        User user = this.userService.getUserByUsername(username);

        return racesPilotsService.updateRacesPilots(newRacesPilots, idRace, idPilot, user.getId());
    }

    @DeleteMapping("/races/{idRace}/pilots/{idPilot}")
    void deleteRacesPilots(@PathVariable Long idRace,
                           @PathVariable Long idPilot,
                           @RequestHeader("Authorization") String token) {
        String username = this.jwtUtils.getUserNameFromJwtToken(token);
        User user = this.userService.getUserByUsername(username);

        racesPilotsService.deleteRacesPilots(idRace, idPilot, user.getId());
    }

}
