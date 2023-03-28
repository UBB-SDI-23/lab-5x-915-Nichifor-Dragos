package hw4.hw4.Advice;

import hw4.hw4.Exception.CarNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class CarNotFoundAdvice {

    @ResponseBody // signals that this advice is rendered straight into the response body.
    @ExceptionHandler(CarNotFoundException.class) // configures the advice to only respond if an CarNotFoundException is thrown
    @ResponseStatus(HttpStatus.NOT_FOUND) // says to issue an HttpStatus.NOT_FOUND, i.e. an HTTP 404
    String carNotFoundHandler(CarNotFoundException ex) {
        return ex.getMessage();
    }

}