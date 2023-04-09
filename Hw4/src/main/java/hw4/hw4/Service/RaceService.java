package hw4.hw4.Service;

import hw4.hw4.DTO.Race.RaceDTO_PilotStatistic;
import hw4.hw4.DTO.Race.RaceDTO_PilotStatistic_CountryUSA;
import hw4.hw4.Entity.Pilot;
import hw4.hw4.Entity.Race;
import hw4.hw4.Entity.RacePilot.RacesPilots;
import hw4.hw4.Exception.RaceNotFoundException;
import hw4.hw4.Repository.RaceRepository;
import hw4.hw4.Repository.RacesPilotsRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class RaceService {

    private final RaceRepository raceRepository;
    private final RacesPilotsRepository racesPilotsRepository;

    public RaceService(RaceRepository raceRepository, RacesPilotsRepository racesPilotsRepository)
    {
        this.raceRepository = raceRepository;
        this.racesPilotsRepository = racesPilotsRepository;
    }

    public List<Race> getAllRaces() {
        return raceRepository.findAll().stream().limit(100).collect(Collectors.toList());
    }

    public Race getOneRace(Long id) {
        return raceRepository.findById(id)
                .orElseThrow(() -> new RaceNotFoundException(id));
    }

    public List<Pilot> getAllPilotsFromRace(Long raceId) {
        List<Pilot> pilots = new ArrayList<>();
        for (RacesPilots racesPilots : this.racesPilotsRepository.findAll())
        {
            if (Objects.equals(racesPilots.getRace().getId(), raceId))
                pilots.add(racesPilots.getPilot());
        }
        return pilots;
    }

    public List<RaceDTO_PilotStatistic> getRacesWithNumberOfPilotsDesc() {
        return this.raceRepository.getRacesWithNumberOfPilotsDesc().stream().limit(100).collect(Collectors.toList());
    }

    public List<RaceDTO_PilotStatistic_CountryUSA> getRacesFromUSAWithNumberOfPilotsDesc() {
        return this.raceRepository.getRacesFromUSAWithNumberOfPilotsDesc().stream().limit(100).collect(Collectors.toList());
    }

    public Race addRace(Race newRace) {
        return raceRepository.save(newRace);
    }

    public Race updateRace(Race newRace, Long id) {
        return raceRepository.findById(id)
                .map(race -> {
                    race.setName(newRace.getName());
                    race.setDate(newRace.getDate());
                    race.setNumberOfLaps(newRace.getNumberOfLaps());
                    race.setCountry(newRace.getCountry());
                    race.setLapLength(newRace.getLapLength());
                    return raceRepository.save(race);
                })
                .orElseGet(() -> {
                    newRace.setId(id);
                    return raceRepository.save(newRace);
                });
    }

    public void deleteRace(Long id) {
        raceRepository.deleteById(id);
    }

}
