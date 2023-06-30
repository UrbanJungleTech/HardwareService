package frentz.daniel.hardwareservice.exception;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ErrorHandlerTest {

    @InjectMocks
    private ErrorHandler errorHandler;
    @Test
    void handleError() {
        HttpStatus httpStatus = HttpStatus.CONFLICT;
        String message = "error message";
        StandardErrorException standardErrorException = new StandardErrorException();
        standardErrorException.setStatus(httpStatus);
        standardErrorException.setMessage(message);

        ResponseEntity<WebRequestException> response = this.errorHandler.handleError(standardErrorException);

        assertEquals(httpStatus.value(), response.getStatusCodeValue());
        assertEquals(message, response.getBody().getMessage());
    }

    @Test
    void fallback() {
        RuntimeException runtimeException = new RuntimeException("error message");
        ResponseEntity<WebRequestException> response = this.errorHandler.fallback(runtimeException);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getStatusCodeValue());
        assertEquals("Unknown error", response.getBody().getMessage());
    }
}
