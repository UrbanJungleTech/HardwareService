package urbanjungletech.hardwareservice.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class ErrorHandler {

    Logger logger = LoggerFactory.getLogger(ErrorHandler.class);


    @ExceptionHandler(value=StandardErrorException.class)
    public ResponseEntity<WebRequestException> handleError(StandardErrorException exception){
        WebRequestException error = new WebRequestException();
        error.setMessage(exception.getMessage());
        error.setHttpStatus(exception.getStatus().value());
        return ResponseEntity.status(exception.getStatus()).body(error);
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

}
