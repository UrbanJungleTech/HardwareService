package frentz.daniel.hardwareservice.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NotFoundExceptionTest {

    @Test
    void standardNotFoundExceptionWithStringId() {
        String id = "1";
        String entityName = "entityName";
        NotFoundException notFoundException = new NotFoundException(entityName, id);
        assertEquals(entityName + " not found with id of " + id, notFoundException.getMessage());
    }

    @Test
    void standardNotFoundExceptionWithIntId() {
        long id = 1;
        String entityName = "entityName";
        NotFoundException notFoundException = new NotFoundException(entityName, id);
        assertEquals(entityName + " not found with id of " + id, notFoundException.getMessage());
    }
}
