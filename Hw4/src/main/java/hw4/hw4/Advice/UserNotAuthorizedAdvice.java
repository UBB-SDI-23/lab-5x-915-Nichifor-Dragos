package hw4.hw4.Advice;

import hw4.hw4.Exception.UserNotAuthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class UserNotAuthorizedAdvice {

    @ResponseBody
    @ExceptionHandler(UserNotAuthorizedException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String userNotAuthorizedHandler(UserNotAuthorizedException ex) {
        return ex.getMessage();
    }

}
