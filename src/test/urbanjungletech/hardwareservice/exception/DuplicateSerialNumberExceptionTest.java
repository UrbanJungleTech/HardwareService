package urbanjungletech.hardwareservice.exception;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import urbanjungletech.hardwareservice.exception.exception.DuplicateSerialNumberException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DuplicateSerialNumberExceptionTest {

    @Test
    void testConstructor() {
        DuplicateSerialNumberException duplicateSerialNumberException = new DuplicateSerialNumberException();
        String message = duplicateSerialNumberException.getMessage();
        HttpStatus expectedStatus = HttpStatus.CONFLICT;
        assertEquals(expectedStatus, duplicateSerialNumberException.getHttpStatus());
        assertEquals(message, duplicateSerialNumberException.getMessage());
    }
}
