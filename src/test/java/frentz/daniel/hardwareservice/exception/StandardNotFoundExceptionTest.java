package frentz.daniel.hardwareservice.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StandardNotFoundExceptionTest {

    @Test
    void standardNotFoundExceptionWithStringId() {
        String id = "1";
        String entityName = "entityName";
        StandardNotFoundException standardNotFoundException = new StandardNotFoundException(entityName, id);
        assertEquals(entityName + " not found with id of " + id, standardNotFoundException.getMessage());
    }

    @Test
    void standardNotFoundExceptionWithIntId() {
        long id = 1;
        String entityName = "entityName";
        StandardNotFoundException standardNotFoundException = new StandardNotFoundException(entityName, id);
        assertEquals(entityName + " not found with id of " + id, standardNotFoundException.getMessage());
    }
}
