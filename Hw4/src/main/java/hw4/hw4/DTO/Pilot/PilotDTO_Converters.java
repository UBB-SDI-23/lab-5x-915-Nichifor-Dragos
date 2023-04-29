package hw4.hw4.DTO.Pilot;

import hw4.hw4.DTO.Race.RaceDTO_All;
import hw4.hw4.Entity.Pilot;
import hw4.hw4.Entity.Race;

public class PilotDTO_Converters {

    public static PilotDTO_All convertToPilotDTO_All(Pilot pilot, Long countRaces) {
        PilotDTO_All newPilot = new PilotDTO_All();
        newPilot.setId(pilot.getId());
        newPilot.setFirstName(pilot.getFirstName());
        newPilot.setLastName(pilot.getLastName());
        newPilot.setDate(pilot.getDate());
        newPilot.setDrivingExperience(pilot.getDrivingExperience());
        newPilot.setNationality(pilot.getNationality());
        newPilot.setNumberOfRaces(countRaces);
        newPilot.setUsername(pilot.getUser().getUsername());
        return newPilot;
    }

}
