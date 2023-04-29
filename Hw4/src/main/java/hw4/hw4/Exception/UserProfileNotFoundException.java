package hw4.hw4.Exception;

public class UserProfileNotFoundException extends RuntimeException {

    public UserProfileNotFoundException(Long id) {
        super("Could not find user profile " + id);
    }

}
