package hw4.hw4.DTO.Race;

import hw4.hw4.Entity.Race;

public class RaceDTO_Converters {

    public static RaceDTO_All convertToRaceDTO_All(Race race, Long countParticipants) {
        RaceDTO_All newRace = new RaceDTO_All();
        newRace.setId(race.getId());
        newRace.setName(race.getName());
        newRace.setCountry(race.getCountry());
        newRace.setDate(race.getDate());
        newRace.setLapLength(race.getLapLength());
        newRace.setNumberOfLaps(race.getNumberOfLaps());
        newRace.setNumberOfParticipants(countParticipants);
        newRace.setUsername(race.getUser().getUsername());
        return newRace;
    }

}
