package hw4.hw4.Advice;

import hw4.hw4.Exception.UserProfileNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class UserProfileNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(UserProfileNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String userProfileNotFoundHandler(UserProfileNotFoundException ex) {
        return ex.getMessage();
    }

}
