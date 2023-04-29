package hw4.hw4.Exception;

public class UserNotAuthorizedException extends RuntimeException {

    public UserNotAuthorizedException(String username) {
        super("The user with username " + username + " is not authorized.");
    }
}
