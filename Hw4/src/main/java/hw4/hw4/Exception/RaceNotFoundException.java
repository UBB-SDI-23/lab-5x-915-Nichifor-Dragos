package hw4.hw4.Exception;

public class RaceNotFoundException extends RuntimeException {

    public RaceNotFoundException(Long id) {
        super("Could not find race " + id);
    }
}

