package hw4.hw4.Exception;

public class RacesPilotsNotFoundException extends RuntimeException {

    public RacesPilotsNotFoundException(Long id) {
        super("Could not find races pilots " + id);
    }
}