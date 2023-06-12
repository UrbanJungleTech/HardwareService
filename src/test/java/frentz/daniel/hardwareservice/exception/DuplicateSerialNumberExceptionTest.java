package frentz.daniel.hardwareservice.exception;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.*;

class DuplicateSerialNumberExceptionTest {

    @Test
    void testConstructor() {
        DuplicateSerialNumberException duplicateSerialNumberException = new DuplicateSerialNumberException();
        String message = duplicateSerialNumberException.getMessage();
        HttpStatus expectedStatus = HttpStatus.CONFLICT;
        assertEquals(expectedStatus, duplicateSerialNumberException.getStatus());
        assertEquals(message, duplicateSerialNumberException.getMessage());
    }
}
