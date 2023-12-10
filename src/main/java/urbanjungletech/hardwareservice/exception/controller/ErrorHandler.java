package urbanjungletech.hardwareservice.exception.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import urbanjungletech.hardwareservice.exception.exception.InvalidSensorConfigurationException;
import urbanjungletech.hardwareservice.exception.exception.StandardErrorException;
import urbanjungletech.hardwareservice.exception.exception.WebRequestException;


@ControllerAdvice
public class ErrorHandler {

    Logger logger = LoggerFactory.getLogger(ErrorHandler.class);


    @ExceptionHandler(value= StandardErrorException.class)
    public ResponseEntity<WebRequestException> handleError(StandardErrorException exception){
        WebRequestException error = new WebRequestException();
        error.setMessage(exception.getMessage());
        error.setHttpStatus(exception.getHttpStatus().intValue());
        return ResponseEntity.status(exception.getHttpStatus()).body(error);
    }

    @ExceptionHandler(value=RuntimeException.class)
    public ResponseEntity<WebRequestException> fallback(Exception ex){
        WebRequestException error = new WebRequestException();
        error.setMessage("Unknown error");
        error.setHttpStatus(500);
        logger.debug("unknown error is {}: ", ex.getMessage());
        ex.printStackTrace();
        return ResponseEntity.status(500).body(error);
    }

    @ExceptionHandler(value= InvalidSensorConfigurationException.class)
    public ResponseEntity<WebRequestException> handleInvalidSensorConfigurationException(InvalidSensorConfigurationException exception){
        WebRequestException error = new WebRequestException();
        error.setMessage(exception.getMessage());
        error.setHttpStatus(400);
        return ResponseEntity.status(400).body(error);
    }

}
