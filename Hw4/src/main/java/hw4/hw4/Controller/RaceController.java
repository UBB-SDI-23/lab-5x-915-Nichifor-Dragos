package hw4.hw4.Controller;

import hw4.hw4.DTO.Pilot.PilotDTO;
import hw4.hw4.DTO.Pilot.PilotDTO_All;
import hw4.hw4.DTO.Pilot.PilotDTO_One;
import hw4.hw4.DTO.Race.*;
import hw4.hw4.Entity.Pilot;
import hw4.hw4.Entity.Race;
import hw4.hw4.Service.RaceService;
import javax.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
// indicates that the data returned by each method will be written straight into the response body instead of rendering a template.
@RequestMapping("/api")
public class RaceController {

    @Autowired
    private ModelMapper modelMapper;

    private final RaceService raceService;

    RaceController(RaceService raceService) {this.raceService = raceService;}

    @GetMapping("/race") // get all the races
    List<RaceDTO_All> allRaces(@RequestParam(defaultValue = "0") Integer pageNo, @RequestParam(defaultValue = "50") Integer pageSize) {
        return raceService.getAllRaces(pageNo, pageSize);
    }

    @GetMapping("/race/{id}") // get a race by its id
    RaceDTO_One oneRace(@PathVariable Long id) {
        return this.convertToRaceDTO_One(raceService.getOneRace(id));
    }

    @GetMapping("/race/{id}/pilot") // get all the pilots which will attend to the race
    List<PilotDTO_All> oneRacePilots(@PathVariable Long id) {return raceService.getAllPilotsFromRace(id).stream().map(this::convertToPilotDTO_All).collect(Collectors.toList());}

    @GetMapping("/race/pilots-statistic") // get the race-pilot statistic
    List<RaceDTO_PilotStatistic> getRacesWithNumberOfPilotsDesc() {
        return this.raceService.getRacesWithNumberOfPilotsDesc();
    }

    @GetMapping("/race/USA-pilots-statistic") // get the pilots that participate in a race in the USA
    List<RaceDTO_PilotStatistic_CountryUSA> getRacesFromUSAWithNumberOfPilotsDesc() {
        return this.raceService.getRacesFromUSAWithNumberOfPilotsDesc();
    }

    @GetMapping("/race/count") // get the number of races
    Long getRacesCount() {
        return this.raceService.getRacesCount();
    }

    @PostMapping("/user/race") // add a new race
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    Race newRace(@Valid @RequestBody Race newRace) {
        return raceService.addRace(newRace);
    }

    @PutMapping("/user/race/{id}") // update a race by its id
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    Race replaceRace(@Valid @RequestBody Race newRace, @PathVariable Long id) {
        return raceService.updateRace(newRace, id);
    }

    @DeleteMapping("/admin/race/{id}") // delete a race by its id
    @PreAuthorize("hasRole('ADMIN')")
    void deleteRace(@PathVariable Long id) {
        raceService.deleteRace(id);
    }

    private RaceDTO_All convertToRaceDTO_All(Race race) {
        return this.modelMapper.map(race, RaceDTO_All.class);
    }

    private RaceDTO_One convertToRaceDTO_One(Race pilot) {
        return this.modelMapper.map(pilot, RaceDTO_One.class);
    }


    private PilotDTO_All convertToPilotDTO_All(Pilot pilot) {
        return this.modelMapper.map(pilot, PilotDTO_All.class);
    }

}
