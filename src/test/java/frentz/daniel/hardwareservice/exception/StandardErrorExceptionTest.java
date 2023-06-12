package frentz.daniel.hardwareservice.exception;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class StandardErrorExceptionTest {

    @Test
    void setMetadata() {
        StandardErrorException standardErrorException = new StandardErrorException();
        Map<String, String> metadata = new HashMap<>();
        standardErrorException.setMetadata(metadata);
        assertSame(metadata, standardErrorException.metadata);
    }

    @Test
    void setStatus() {
        StandardErrorException standardErrorException = new StandardErrorException();
        HttpStatus httpStatus = HttpStatus.ACCEPTED;
        standardErrorException.setStatus(httpStatus);
        assertSame(httpStatus, standardErrorException.status);
    }

    @Test
    void setMessage() {
        StandardErrorException standardErrorException = new StandardErrorException();
        String message = "message";
        standardErrorException.setMessage(message);
        assertSame(message, standardErrorException.message);
    }
}
