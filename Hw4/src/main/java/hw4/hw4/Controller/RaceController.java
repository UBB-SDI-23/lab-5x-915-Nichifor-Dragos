package hw4.hw4.Controller;

import hw4.hw4.DTO.Pilot.PilotDTO;
import hw4.hw4.DTO.Pilot.PilotDTO_All;
import hw4.hw4.DTO.Pilot.PilotDTO_One;
import hw4.hw4.DTO.Race.*;
import hw4.hw4.Entity.Pilot;
import hw4.hw4.Entity.Race;
import hw4.hw4.Service.RaceService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
    List<RaceDTO_All> allRaces() {
        return raceService.getAllRaces().stream().map(this::convertToRaceDTO_All).collect(Collectors.toList());
    }

    @GetMapping("/race/{id}") // get a race by its id
    RaceDTO_One oneRace(@PathVariable Long id) {
        return this.convertToRaceDTO_One(raceService.getOneRace(id));
    }

    @GetMapping("/race/{id}/pilot") // get all the pilots which will attend to the race
    List<PilotDTO_All> oneRacePilots(@PathVariable Long id) {return raceService.getAllPilotsFromRace(id).stream().map(this::convertToPilotDTO_All).collect(Collectors.toList());}

    @GetMapping("/race/pilots-statistic")
    List<RaceDTO_PilotStatistic> getRacesWithNumberOfPilotsDesc() {
        return this.raceService.getRacesWithNumberOfPilotsDesc();
    }

    @GetMapping("/race/USA-pilots-statistic")
    List<RaceDTO_PilotStatistic_CountryUSA> getRacesFromUSAWithNumberOfPilotsDesc() {
        return this.raceService.getRacesFromUSAWithNumberOfPilotsDesc();
    }

    @PostMapping("/race") // add a new race
    Race newRace(@Valid @RequestBody Race newRace) {
        return raceService.addRace(newRace);
    }

    @PutMapping("/race/{id}") // update a race given its id
    Race replaceRace(@Valid @RequestBody Race newRace, @PathVariable Long id) {
        return raceService.updateRace(newRace, id);
    }

    @DeleteMapping("/race/{id}") // delete a race given its id
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
