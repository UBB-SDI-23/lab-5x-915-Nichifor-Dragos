package hw4.hw4.Exception;

import hw4.hw4.Entity.RacePilot.RacesPilotsKey;

public class RacesPilotsNotFoundException extends RuntimeException {

    public RacesPilotsNotFoundException(RacesPilotsKey id) {
        super("Could not find races pilots " + id);
    }
}