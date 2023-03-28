package hw4.hw4.Repository;

import hw4.hw4.DTO.Pilot.PilotDTO_CarStatistic;

import java.util.List;

public interface CustomPilotRepository {

    List<PilotDTO_CarStatistic> getPilotsWithNumberOfCarsAsc();

}
