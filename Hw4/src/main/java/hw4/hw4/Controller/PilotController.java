package hw4.hw4.Controller;

import hw4.hw4.DTO.Car.CarDTO;
import hw4.hw4.DTO.Car.CarDTO_All;
import hw4.hw4.DTO.Pilot.PilotDTO_CarStatistic;
import hw4.hw4.DTO.Pilot.PilotDTO;
import hw4.hw4.DTO.Pilot.PilotDTO_All;
import hw4.hw4.DTO.Pilot.PilotDTO_One;
import hw4.hw4.DTO.Race.RaceDTO;
import hw4.hw4.DTO.Race.RaceDTO_All;
import hw4.hw4.Entity.Car;
import hw4.hw4.Entity.Pilot;
import hw4.hw4.Entity.Race;
import hw4.hw4.Service.PilotService;
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
public class PilotController {

    @Autowired
    private ModelMapper modelMapper;

    private final PilotService pilotService;

    PilotController(PilotService pilotService) {this.pilotService = pilotService;}

    @GetMapping("/pilot") // get all the pilots
    List<PilotDTO_All> allPilots(@RequestParam(defaultValue = "0") Integer pageNo, @RequestParam(defaultValue = "50") Integer pageSize) {
        return pilotService.getAllPilots(pageNo, pageSize).stream().map(this::convertToPilotDTO_All).collect(Collectors.toList());
    }

    @GetMapping("/pilot/{id}") // get a pilot by its id
    PilotDTO_One onePilot(@PathVariable Long id) {
        return convertToPilotDTO_One(pilotService.getOnePilot(id));
    }

    @GetMapping("/pilot/{id}/car") // get all the cars from a pilot given the id
    List<CarDTO_All>  onePilotCars(@PathVariable Long id) {
        return pilotService.getAllCarsFromPilot(id).stream().map(this::convertToCarDTO_All).collect(Collectors.toList());
    }

    @GetMapping("/pilot/{id}/race") // get all the races that the pilot will attend to
    List<RaceDTO_All> onePilotRaces(@PathVariable Long id){return pilotService.getAllRacesFromPilot(id).stream().map(this::convertToRaceDTO_All).collect(Collectors.toList());}

    @GetMapping("/pilot/count")
    Long getPilotCount() {
        return this.pilotService.getPilotCount();
    }

    @GetMapping("/pilot/car-statistic")
    List<PilotDTO_CarStatistic> getPilotsWithNumberOfCarsAsc() {
        return this.pilotService.getPilotsWithNumberOfCarsAsc();
    }

    @PostMapping("/pilot") // add a new pilot
    Pilot newPilot(@Valid @RequestBody Pilot newPilot) {
        return pilotService.addPilot(newPilot);
    }

    @PutMapping("/pilot/{id}") // update a pilot given its id
    Pilot replacePilot(@Valid @RequestBody Pilot newPilot, @PathVariable Long id) {
        return pilotService.updatePilot(newPilot, id);
    }

    @DeleteMapping("/pilot/{id}") // delete a pilot given its id
    void deletePilot(@PathVariable Long id) {
        pilotService.deletePilot(id);
    }

    private PilotDTO_All convertToPilotDTO_All(Pilot pilot) {
        return this.modelMapper.map(pilot, PilotDTO_All.class);
    }

    private PilotDTO_One convertToPilotDTO_One(Pilot pilot) {
        return this.modelMapper.map(pilot, PilotDTO_One.class);
    }

    private CarDTO_All convertToCarDTO_All(Car car) {
        CarDTO_All carDTO = this.modelMapper.map(car, CarDTO_All.class);
        carDTO.setPilotID(car.getPilot().getId());
        return carDTO;
    }

    private RaceDTO_All convertToRaceDTO_All(Race race) {
        return this.modelMapper.map(race, RaceDTO_All.class);
    }

}
