package hw4.hw4.Service;

import hw4.hw4.Entity.Pilot;
import hw4.hw4.Entity.Race;
import hw4.hw4.Entity.RacePilot.RacesPilots;
import hw4.hw4.Entity.RacePilot.RacesPilotsKey;
import hw4.hw4.Entity.User.ERole;
import hw4.hw4.Entity.User.User;
import hw4.hw4.Exception.*;
import hw4.hw4.Repository.PilotRepository;
import hw4.hw4.Repository.RaceRepository;
import hw4.hw4.Repository.RacesPilotsRepository;
import hw4.hw4.Repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class RacesPilotsService {

    private final PilotRepository pilotRepository;
    private final RaceRepository raceRepository;
    private final RacesPilotsRepository racesPilotsRepository;

    private final UserRepository userRepository;

    public RacesPilotsService(PilotRepository pilotRepository, RaceRepository raceRepository, RacesPilotsRepository racesPilotsRepository, UserRepository userRepository) {
        this.pilotRepository = pilotRepository;
        this.raceRepository = raceRepository;
        this.racesPilotsRepository = racesPilotsRepository;
        this.userRepository = userRepository;
    }

    public List<RacesPilots> getAllRacesPilots() {
        return racesPilotsRepository.findAll();
    }

    public RacesPilots getOneRacesPilots(Long idRace, Long idPilot) {
        RacesPilotsKey key = new RacesPilotsKey();
        Pilot pilot = pilotRepository.findById(idPilot).orElseThrow(() -> new PilotNotFoundException(idPilot));
        Race race = raceRepository.findById(idRace).orElseThrow(() -> new PilotNotFoundException(idRace));
        key.setRaceId(race.getId());
        key.setPilotId(pilot.getId());
        return racesPilotsRepository.findById(key).orElseThrow(() -> new RacesPilotsNotFoundException(key));
    }

    public RacesPilots addRacesPilots(RacesPilots newRacesPilots, Long idRace, Long idPilot, Long userID) {
        RacesPilotsKey key = new RacesPilotsKey();
        Pilot pilot = pilotRepository.findById(idPilot).orElseThrow(() -> new PilotNotFoundException(idPilot));
        Race race = raceRepository.findById(idRace).orElseThrow(() -> new PilotNotFoundException(idRace));

        User user = this.userRepository.findById(userID).orElseThrow(() -> new UserNotFoundException(userID));

        if (!Objects.equals(user.getId(), race.getUser().getId()) || !Objects.equals(user.getId(), pilot.getUser().getId())) {
            boolean modOrAdmin = user.getRoles().stream().anyMatch((role) ->
                    role.getName() == ERole.ROLE_ADMIN || role.getName() == ERole.ROLE_MODERATOR
            );

            if (!modOrAdmin) {
                throw new UserNotAuthorizedException(String.format("%s does not have permission to " +
                        "add race-pilot pair (%s, %s)", user.getUsername(), race.getId(), pilot.getId()));
            }
        }

        key.setRaceId(race.getId());
        key.setPilotId(pilot.getId());
        newRacesPilots.setId(key);
        newRacesPilots.setRace(race);
        newRacesPilots.setPilot(pilot);
        return racesPilotsRepository.save(newRacesPilots);
    }

    public RacesPilots updateRacesPilots(RacesPilots newRacesPilots, Long idRace, Long idPilot, Long userID)
    {
        RacesPilotsKey id = new RacesPilotsKey();
        id.setPilotId(idPilot);
        id.setRaceId(idRace);

        Pilot pilot = pilotRepository.findById(idPilot).orElseThrow(() -> new PilotNotFoundException(idPilot));
        Race race = raceRepository.findById(idRace).orElseThrow(() -> new PilotNotFoundException(idRace));

        User user = this.userRepository.findById(userID).orElseThrow(() -> new UserNotFoundException(userID));

        if (!Objects.equals(user.getId(), race.getUser().getId()) || !Objects.equals(user.getId(), pilot.getUser().getId())) {
            boolean modOrAdmin = user.getRoles().stream().anyMatch((role) ->
                    role.getName() == ERole.ROLE_ADMIN || role.getName() == ERole.ROLE_MODERATOR
            );

            if (!modOrAdmin) {
                throw new UserNotAuthorizedException(String.format("%s does not have permission to " +
                        "add race-pilot pair (%s, %s)", user.getUsername(), race.getId(), pilot.getId()));
            }
        }

        return racesPilotsRepository.findById(id)
                .map(racesPilots -> {
                    racesPilots.setNeedAccommodation(newRacesPilots.getNeedAccommodation());
                    racesPilots.setStartPosition(newRacesPilots.getStartPosition());
                    return racesPilotsRepository.save(racesPilots);
                }).orElseThrow(() -> new RacesPilotsNotFoundException(id));
    }

    public void deleteRacesPilots(Long idRace, Long idPilot)
    {
        RacesPilotsKey id = new RacesPilotsKey();
        id.setRaceId(idRace);
        id.setPilotId(idPilot);
        if(!racesPilotsRepository.existsById(id))
            throw new RacesPilotsNotFoundException(id);
        racesPilotsRepository.deleteById(id);
    }

}
