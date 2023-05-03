package hw4.hw4.Service;

import hw4.hw4.DTO.Race.RaceDTO_All;
import hw4.hw4.DTO.Race.RaceDTO_Converters;
import hw4.hw4.DTO.Race.RaceDTO_PilotStatistic;
import hw4.hw4.DTO.Race.RaceDTO_PilotStatistic_CountryUSA;
import hw4.hw4.Entity.Pilot;
import hw4.hw4.Entity.Race;
import hw4.hw4.Entity.RacePilot.RacesPilots;
import hw4.hw4.Entity.User.ERole;
import hw4.hw4.Entity.User.User;
import hw4.hw4.Exception.RaceNotFoundException;
import hw4.hw4.Exception.UserNotAuthorizedException;
import hw4.hw4.Exception.UserNotFoundException;
import hw4.hw4.Repository.RaceRepository;
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
public class RaceService {

    private final RaceRepository raceRepository;
    private final RacesPilotsRepository racesPilotsRepository;

    private final UserRepository userRepository;

    public RaceService(RaceRepository raceRepository, RacesPilotsRepository racesPilotsRepository, UserRepository userRepository) {
        this.raceRepository = raceRepository;
        this.racesPilotsRepository = racesPilotsRepository;
        this.userRepository = userRepository;
    }

    public List<RaceDTO_All> getAllRaces(Integer pageNo, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by("id"));

        return raceRepository.findAll(pageable).getContent().stream().map(
                race -> RaceDTO_Converters.convertToRaceDTO_All(race,
                        this.racesPilotsRepository.countByRaceId(race.getId()))
        ).collect(Collectors.toList());
    }

    public Long getRacesCount() {
        return this.raceRepository.count();
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

    public Race addRace(Race newRace, Long userId) {
        User user = this.userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        newRace.setUser(user);

        boolean userOrModOrAdmin = user.getRoles().stream().anyMatch((role) ->
                role.getName() == ERole.ROLE_ADMIN
                        || role.getName() == ERole.ROLE_MODERATOR
                        || role.getName() == ERole.ROLE_USER
        );
        if (!userOrModOrAdmin) {
            throw new UserNotAuthorizedException(String.format(user.getUsername()));
        }

        return raceRepository.save(newRace);
    }

    public Race updateRace(Race newRace, Long raceID, Long userID) {
        Race race = this.raceRepository.findById(raceID).orElseThrow(() -> new RaceNotFoundException(raceID));
        User user = this.userRepository.findById(userID).orElseThrow(() -> new UserNotFoundException(userID));

        boolean isUser = user.getRoles().stream().anyMatch((role) ->
                role.getName() == ERole.ROLE_USER
        );
        if (!isUser) {
            throw new UserNotAuthorizedException(String.format(user.getUsername()));
        }

        if (!Objects.equals(user.getId(), race.getUser().getId())) {
            boolean modOrAdmin = user.getRoles().stream().anyMatch((role) ->
                    role.getName() == ERole.ROLE_ADMIN || role.getName() == ERole.ROLE_MODERATOR
            );

            if (!modOrAdmin) {
                throw new UserNotAuthorizedException(String.format(user.getUsername()));
            }
        }

        return raceRepository.findById(raceID)
                .map(raceUpdate -> {
                    raceUpdate.setName(newRace.getName());
                    raceUpdate.setDate(newRace.getDate());
                    raceUpdate.setNumberOfLaps(newRace.getNumberOfLaps());
                    raceUpdate.setCountry(newRace.getCountry());
                    raceUpdate.setLapLength(newRace.getLapLength());
                    return raceRepository.save(raceUpdate);
                }).orElseThrow(() -> new RaceNotFoundException(raceID));
    }

    public void deleteRace(Long id, Long userID) {
        User user = this.userRepository.findById(userID).orElseThrow(() -> new UserNotFoundException(userID));

        boolean isAdmin = user.getRoles().stream().anyMatch((role) ->
                role.getName() == ERole.ROLE_ADMIN
        );
        if (!isAdmin) {
            throw new UserNotAuthorizedException(String.format(user.getUsername()));
        }

        if(!raceRepository.existsById(id))
            throw new RaceNotFoundException(id);
        raceRepository.deleteById(id);
    }

}
