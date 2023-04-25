package hw4.hw4.Advice;

import hw4.hw4.Exception.RaceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

public class RaceNotFoundAdvice {

    @ResponseBody // signals that this advice is rendered straight into the response body.
    @ExceptionHandler(RaceNotFoundException.class) // configures the advice to only respond if an RaceNotFoundException is thrown
    @ResponseStatus(HttpStatus.NOT_FOUND) // says to issue an HttpStatus.NOT_FOUND, i.e. an HTTP 404
    String raceNotFoundHandler(RaceNotFoundException ex) {
        return ex.getMessage();
    }

}
