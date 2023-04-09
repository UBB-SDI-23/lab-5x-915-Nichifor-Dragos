package hw4.hw4.Service;

import hw4.hw4.Entity.Pilot;
import hw4.hw4.Entity.Race;
import hw4.hw4.Entity.RacePilot.RacesPilots;
import hw4.hw4.Entity.RacePilot.RacesPilotsKey;
import hw4.hw4.Exception.PilotNotFoundException;
import hw4.hw4.Repository.PilotRepository;
import hw4.hw4.Repository.RaceRepository;
import hw4.hw4.Repository.RacesPilotsRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RacesPilotsService {

    private final PilotRepository pilotRepository;
    private final RaceRepository raceRepository;
    private final RacesPilotsRepository racesPilotsRepository;


    public RacesPilotsService(PilotRepository pilotRepository, RaceRepository raceRepository, RacesPilotsRepository racesPilotsRepository) {
        this.pilotRepository = pilotRepository;
        this.raceRepository = raceRepository;
        this.racesPilotsRepository = racesPilotsRepository;
    }

    public List<RacesPilots> getAllRacesPilots() {
        return racesPilotsRepository.findAll();
    }

    public RacesPilots addRacesPilots(RacesPilots newRacesPilots, Long idRace, Long idPilot) {
        RacesPilotsKey key = new RacesPilotsKey();
        Pilot pilot = pilotRepository.findById(idPilot).orElseThrow(() -> new PilotNotFoundException(idPilot));
        Race race = raceRepository.findById(idRace).orElseThrow(() -> new PilotNotFoundException(idRace));
        key.setRaceId(race.getId());
        key.setPilotId(pilot.getId());
        newRacesPilots.setId(key);
        newRacesPilots.setRace(race);
        newRacesPilots.setPilot(pilot);
        return racesPilotsRepository.save(newRacesPilots);
    }

    public RacesPilots updateRacesPilots(RacesPilots newRacesPilots, Long idRace, Long idPilot)
    {
        RacesPilotsKey id = new RacesPilotsKey();
        id.setPilotId(idPilot);
        id.setRaceId(idRace);
        return racesPilotsRepository.findById(id)
                .map(racesPilots -> {
                    racesPilots.setNeedAccommodation(newRacesPilots.getNeedAccommodation());
                    racesPilots.setStartPosition(newRacesPilots.getStartPosition());
                    return racesPilotsRepository.save(racesPilots);
                })
                .orElseGet(() -> {
                    newRacesPilots.setId(id);
                    return racesPilotsRepository.save(newRacesPilots);
                });
    }

    public void deleteRacesPilots(Long idRace, Long idPilot)
    {
        RacesPilotsKey id = new RacesPilotsKey();
        id.setRaceId(idRace);
        id.setPilotId(idPilot);
        racesPilotsRepository.deleteById(id);
    }

}
