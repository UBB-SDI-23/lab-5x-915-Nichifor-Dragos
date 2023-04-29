package hw4.hw4.Service;

import hw4.hw4.DTO.Pilot.PilotDTO_All;
import hw4.hw4.DTO.Pilot.PilotDTO_CarStatistic;
import hw4.hw4.DTO.Pilot.PilotDTO_Converters;
import hw4.hw4.Entity.Car;
import hw4.hw4.Entity.Pilot;
import hw4.hw4.Entity.Race;
import hw4.hw4.Entity.RacePilot.RacesPilots;
import hw4.hw4.Entity.User.ERole;
import hw4.hw4.Entity.User.User;
import hw4.hw4.Exception.PilotNotFoundException;
import hw4.hw4.Exception.RaceNotFoundException;
import hw4.hw4.Exception.UserNotAuthorizedException;
import hw4.hw4.Exception.UserNotFoundException;
import hw4.hw4.Repository.CarRepository;
import hw4.hw4.Repository.PilotRepository;
import hw4.hw4.Repository.RacesPilotsRepository;
import hw4.hw4.Repository.UserRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class PilotService {

    private final CarRepository carRepository;
    private final PilotRepository pilotRepository;

    private final UserRepository userRepository;

    private final RacesPilotsRepository racesPilotsRepository;

    public PilotService(CarRepository carRepository, PilotRepository pilotRepository, UserRepository userRepository, RacesPilotsRepository racesPilotsRepository)
    {
        this.carRepository = carRepository;
        this.pilotRepository = pilotRepository;
        this.userRepository = userRepository;
        this.racesPilotsRepository = racesPilotsRepository;
    }

    public List<PilotDTO_All> getAllPilots(Integer pageNo, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by("id"));

        return pilotRepository.findAll(pageable).getContent().stream().map(
                pilot -> PilotDTO_Converters.convertToPilotDTO_All(pilot,
                        this.racesPilotsRepository.countByPilotId(pilot.getId()))
        ).collect(Collectors.toList());
    }

    public Long getPilotCount() {
        return pilotRepository.count();
    }

    public List<PilotDTO_CarStatistic> getPilotsWithNumberOfCarsAsc() {
        return this.pilotRepository.getPilotsWithNumberOfCarsAsc();
    }

    public List<Pilot> searchPilotsByNameFullText(String name) {
        return this.pilotRepository.findTop20BySearchTerm(name);
    }

    public List<Car> getAllCarsFromPilot(Long id){
        return this.carRepository.findByPilotId(id);
    }

    public List<Race> getAllRacesFromPilot(Long pilotId) {
        List<Race> races = new ArrayList<>();
        for (RacesPilots racesPilots : this.racesPilotsRepository.findAll()) {
            if (Objects.equals(racesPilots.getPilot().getId(), pilotId))
                races.add(racesPilots.getRace());
        }
        return races;
    }

    public Pilot getOnePilot(Long id) {
        return pilotRepository.findById(id)
                .orElseThrow(() -> new PilotNotFoundException(id));
    }

    public Pilot addPilot(Pilot newPilot, Long userID) {
        User user = this.userRepository.findById(userID).orElseThrow(() -> new UserNotFoundException(userID));
        newPilot.setUser(user);

        return pilotRepository.save(newPilot);
    }

    public Pilot updatePilot(Pilot newPilot, Long pilotID, Long userID) {
        Pilot pilot = this.pilotRepository.findById(pilotID).orElseThrow(() -> new RaceNotFoundException(pilotID));
        User user = this.userRepository.findById(userID).orElseThrow(() -> new UserNotFoundException(userID));

        if (!Objects.equals(user.getId(), pilot.getUser().getId())) {
            boolean modOrAdmin = user.getRoles().stream().anyMatch((role) ->
                    role.getName() == ERole.ROLE_ADMIN || role.getName() == ERole.ROLE_MODERATOR
            );

            if (!modOrAdmin) {
                throw new UserNotAuthorizedException(String.format("%s does not have permission to " +
                        "update pilot %s", user.getUsername(), pilot.getId()));
            }
        }

        return pilotRepository.findById(pilotID)
                .map(pilotUpdate -> {
                    pilotUpdate.setFirstName(newPilot.getFirstName());
                    pilotUpdate.setLastName(newPilot.getLastName());
                    pilotUpdate.setNationality(newPilot.getNationality());
                    pilotUpdate.setDate(newPilot.getDate());
                    pilotUpdate.setDrivingExperience(newPilot.getDrivingExperience());
                    return pilotRepository.save(pilotUpdate);
                }).orElseThrow(() -> new PilotNotFoundException(pilotID));
    }

    public void deletePilot(Long id) {
        if(!pilotRepository.existsById(id))
            throw new PilotNotFoundException(id);
        pilotRepository.deleteById(id);
    }

}
