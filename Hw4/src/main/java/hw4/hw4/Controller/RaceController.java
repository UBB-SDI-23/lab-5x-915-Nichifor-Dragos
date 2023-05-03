package hw4.hw4.Controller;

import hw4.hw4.DTO.Pilot.PilotDTO_All;
import hw4.hw4.DTO.Race.*;
import hw4.hw4.Entity.Pilot;
import hw4.hw4.Entity.Race;
import hw4.hw4.Entity.User.User;
import hw4.hw4.Security.JWT.JwtUtils;
import hw4.hw4.Service.RaceService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import hw4.hw4.Service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(allowCredentials = "true", origins = {"http://localhost:4200", "https://racemasters.netlify.app"})
@RestController
@RequestMapping("/api")
@Validated
public class RaceController {

    @Autowired
    private ModelMapper modelMapper;

    private final UserService userService;

    private final RaceService raceService;

    private final JwtUtils jwtUtils;

    RaceController(UserService userService, RaceService raceService, JwtUtils jwtUtils) {
        this.userService = userService;
        this.raceService = raceService;
        this.jwtUtils = jwtUtils;
    }

    @GetMapping("/race")
    List<RaceDTO_All> allRaces(@RequestParam(defaultValue = "0") Integer pageNo,
                               @RequestParam(defaultValue = "50") Integer pageSize) {
        return raceService.getAllRaces(pageNo, pageSize);
    }

    @GetMapping("/race/count")
    Long getRacesCount() {
        return this.raceService.getRacesCount();
    }


    @GetMapping("/race/{id}")
    RaceDTO_One oneRace(@PathVariable Long id) {
        return this.convertToRaceDTO_One(raceService.getOneRace(id));
    }

    @GetMapping("/race/{id}/pilot")
    List<PilotDTO_All> oneRacePilots(@PathVariable Long id) {
        return raceService.getAllPilotsFromRace(id).stream().map(this::convertToPilotDTO_All).collect(Collectors.toList());
    }

    @GetMapping("/race/pilots-statistic")
    List<RaceDTO_PilotStatistic> getRacesWithNumberOfPilotsDesc() {
        return this.raceService.getRacesWithNumberOfPilotsDesc();
    }

    @GetMapping("/race/USA-pilots-statistic")
    List<RaceDTO_PilotStatistic_CountryUSA> getRacesFromUSAWithNumberOfPilotsDesc() {
        return this.raceService.getRacesFromUSAWithNumberOfPilotsDesc();
    }

    @PostMapping("/race")
    Race newRace(@Valid @RequestBody Race newRace,
                 @RequestHeader("Authorization") String token) {
        String username = this.jwtUtils.getUserNameFromJwtToken(token);
        User user = this.userService.getUserByUsername(username);

        return raceService.addRace(newRace, user.getId());
    }

    @PutMapping("/race/{id}")
    Race replaceRace(@Valid @RequestBody Race newRace,
                     @PathVariable Long id,
                     @RequestHeader("Authorization") String token) {
        String username = this.jwtUtils.getUserNameFromJwtToken(token);
        User user = this.userService.getUserByUsername(username);

        return raceService.updateRace(newRace, id, user.getId());
    }

    @DeleteMapping("/race/{id}")
    void deleteRace(@PathVariable Long id,
                    @RequestHeader("Authorization") String token) {
        String username = this.jwtUtils.getUserNameFromJwtToken(token);
        User user = this.userService.getUserByUsername(username);

        raceService.deleteRace(id, user.getId());
    }

    private RaceDTO_One convertToRaceDTO_One(Race race) {
        RaceDTO_One raceDTO = this.modelMapper.map(race, RaceDTO_One.class);
        raceDTO.setUsername(race.getUser().getUsername());
        return raceDTO;
    }


    private PilotDTO_All convertToPilotDTO_All(Pilot pilot) {
        return this.modelMapper.map(pilot, PilotDTO_All.class);
    }

}
