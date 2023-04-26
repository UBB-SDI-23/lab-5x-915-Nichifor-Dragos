package hw4.hw4.Controller;

import hw4.hw4.Entity.Race;
import hw4.hw4.Entity.RacePilot.RacesPilots;
import hw4.hw4.Service.RacesPilotsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
// indicates that the data returned by each method will be written straight into the response body instead of rendering a template.
@RequestMapping("/api")
public class RacesPilotsController {

    private final RacesPilotsService racesPilotsService;

    private RacesPilotsController (RacesPilotsService racesPilotsService) {this.racesPilotsService = racesPilotsService;}

    @GetMapping("/public/races/pilots") // get all race pilot pairs
    List<RacesPilots> allRacesPilots() {
        return this.racesPilotsService.getAllRacesPilots();
    }

    @GetMapping("/public/races/{idRace}/pilots/{idPilot}") // get one race pilot pair
    RacesPilots oneRacesPilots(@PathVariable Long idRace, @PathVariable Long idPilot) {
        return this.racesPilotsService.getOneRacesPilots(idRace, idPilot);
    }

    @PostMapping("/user/races/{idRace}/pilots/{idPilot}") // add a new race pilot pair
    RacesPilots newRacesPilots(@RequestBody RacesPilots newRacesPilots, @PathVariable Long idRace, @PathVariable Long idPilot) {
        return racesPilotsService.addRacesPilots(newRacesPilots, idRace, idPilot);
    }

    @PutMapping("/user/races/{idRace}/pilots/{idPilot}") // update a race given its id
    RacesPilots replaceRacesPilots(@RequestBody RacesPilots newRacesPilots, @PathVariable Long idRace, @PathVariable Long idPilot) {
        return racesPilotsService.updateRacesPilots(newRacesPilots, idRace, idPilot);
    }

    @DeleteMapping("/admin/races/{idRace}/pilots/{idPilot}") // delete a race given its id
    void deleteRacesPilots(@PathVariable Long idRace, @PathVariable Long idPilot) {
        racesPilotsService.deleteRacesPilots(idRace, idPilot);
    }

}
