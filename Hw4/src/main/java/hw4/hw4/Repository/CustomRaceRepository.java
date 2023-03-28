package hw4.hw4.Repository;

import hw4.hw4.DTO.Race.RaceDTO_PilotStatistic;
import hw4.hw4.DTO.Race.RaceDTO_PilotStatistic_CountryUSA;

import java.util.List;

public interface CustomRaceRepository {

    List<RaceDTO_PilotStatistic> getRacesWithNumberOfPilotsDesc();

    List<RaceDTO_PilotStatistic_CountryUSA> getRacesFromUSAWithNumberOfPilotsDesc();

}
