package frentz.daniel.hardwareservice.exception;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class StandardErrorTest {


    @Test
    void setMessage() {
        StandardError standardError = new StandardError();
        String message = "error message";
        standardError.setMessage(message);
        assertEquals(message, standardError.getMessage());
    }

    @Test
    void setHttpStatus() {
        StandardError standardError = new StandardError();
        int httpStatus = 500;
        standardError.setHttpStatus(httpStatus);
        assertEquals(httpStatus, standardError.getHttpStatus());
    }
}
