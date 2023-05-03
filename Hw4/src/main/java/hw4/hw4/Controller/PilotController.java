package hw4.hw4.Controller;

import hw4.hw4.DTO.Car.CarDTO_All;
import hw4.hw4.DTO.Pilot.PilotDTO_CarStatistic;
import hw4.hw4.DTO.Pilot.PilotDTO_All;
import hw4.hw4.DTO.Pilot.PilotDTO_One;
import hw4.hw4.DTO.Race.RaceDTO_All;
import hw4.hw4.Entity.Car;
import hw4.hw4.Entity.Pilot;
import hw4.hw4.Entity.Race;
import hw4.hw4.Entity.User.User;
import hw4.hw4.Security.JWT.JwtUtils;
import hw4.hw4.Service.PilotService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import hw4.hw4.Service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(allowCredentials = "true", origins = {"http://localhost:4200", "https://racemasters.netlify.app"})
@RestController
@RequestMapping("/api")
public class PilotController {

    @Autowired
    private ModelMapper modelMapper;

    private final PilotService pilotService;

    private final UserService userService;

    private final JwtUtils jwtUtils;

    PilotController(PilotService pilotService, UserService userService, JwtUtils jwtUtils) {
        this.pilotService = pilotService;
        this.userService = userService;
        this.jwtUtils = jwtUtils;
    }

    @GetMapping("/pilot")
    List<PilotDTO_All> allPilots(@RequestParam(defaultValue = "0") Integer pageNo,
                                 @RequestParam(defaultValue = "50") Integer pageSize) {
        return pilotService.getAllPilots(pageNo, pageSize);
    }

    @GetMapping("/pilot/{id}")
    PilotDTO_One onePilot(@PathVariable Long id) {
        return convertToPilotDTO_One(pilotService.getOnePilot(id));
    }

    @GetMapping("/pilot/{id}/car")
    List<CarDTO_All>  onePilotCars(@PathVariable Long id) {
        return pilotService.getAllCarsFromPilot(id).stream().map(this::convertToCarDTO_All).collect(Collectors.toList());
    }

    @GetMapping("/pilot/{id}/race")
    List<RaceDTO_All> onePilotRaces(@PathVariable Long id){
        return pilotService.getAllRacesFromPilot(id).stream().map(this::convertToRaceDTO_All).collect(Collectors.toList());
    }

    @GetMapping("/pilot-search")
    List<Pilot> getPilotsByName(@RequestParam(required = false) String name) {
        return this.pilotService.searchPilotsByNameFullText(name);
    }

    @GetMapping("/pilot/count")
    Long getPilotCount() {
        return this.pilotService.getPilotCount();
    }

    @GetMapping("/pilot/cars-statistic")
    List<PilotDTO_CarStatistic> getPilotsWithNumberOfCarsAsc() {
        return this.pilotService.getPilotsWithNumberOfCarsAsc();
    }

    @PostMapping("/pilot")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    Pilot newPilot(@Valid @RequestBody Pilot newPilot,
                   HttpServletRequest request) {
        String token = this.jwtUtils.getJwtFromCookies(request);
        String username = this.jwtUtils.getUserNameFromJwtToken(token);
        User user = this.userService.getUserByUsername(username);

        return pilotService.addPilot(newPilot, user.getId());
    }

    @PutMapping("/pilot/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    Pilot replacePilot(@Valid @RequestBody Pilot newPilot,
                       @PathVariable Long id,
                       HttpServletRequest request) {
        String token = this.jwtUtils.getJwtFromCookies(request);
        String username = this.jwtUtils.getUserNameFromJwtToken(token);
        User user = this.userService.getUserByUsername(username);

        return pilotService.updatePilot(newPilot, id, user.getId());
    }

    @DeleteMapping("/pilot/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    void deletePilot(@PathVariable Long id) {
        pilotService.deletePilot(id);
    }

    private PilotDTO_One convertToPilotDTO_One(Pilot pilot) {
        PilotDTO_One pilotDTO = this.modelMapper.map(pilot, PilotDTO_One.class);
        pilotDTO.setUsername(pilot.getUser().getUsername());
        return pilotDTO;
    }

    private CarDTO_All convertToCarDTO_All(Car car) {
        CarDTO_All carDTO = this.modelMapper.map(car, CarDTO_All.class);
        carDTO.setPilotID(car.getPilot().getId());
        carDTO.setUsername(car.getUser().getUsername());
        return carDTO;
    }

    private RaceDTO_All convertToRaceDTO_All(Race race) {
        return this.modelMapper.map(race, RaceDTO_All.class);
    }

}
