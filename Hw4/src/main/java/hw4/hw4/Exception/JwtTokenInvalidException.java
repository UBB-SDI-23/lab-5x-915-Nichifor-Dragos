package hw4.hw4.Exception;

public class JwtTokenInvalidException extends RuntimeException {

    public JwtTokenInvalidException(String token) {
        super("Could not validate. Token " + token + " is invalid.");
    }

}
