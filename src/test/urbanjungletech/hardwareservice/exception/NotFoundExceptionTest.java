package urbanjungletech.hardwareservice.exception;

import org.junit.jupiter.api.Test;
import urbanjungletech.hardwareservice.exception.exception.NotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
