package urbanjungletech.hardwareservice.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import urbanjungletech.hardwareservice.exception.exception.DatasourceNotRegisteredException;
import urbanjungletech.hardwareservice.exception.service.ExceptionService;

import org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import urbanjungletech.hardwareservice.exception.exception.DatasourceNotRegisteredException;
import urbanjungletech.hardwareservice.exception.service.ExceptionService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ExceptionServiceImplIT {

    @Autowired
    private ExceptionService exceptionService;

    /**
     * Given an exception of type DatasourceNotRegisteredException
     * When the method throwSystemException of the ExceptionService is called
     * Then the exception is thrown
     * And the message of the exception is "Datasource not registered"
     */
    @Test
    public void throwSystemException_givenTheThrowSystemExceptionMethodWasCalled() {
        DatasourceNotRegisteredException exception = assertThrows(DatasourceNotRegisteredException.class, () -> {
            RuntimeException cause = new RuntimeException();
            this.exceptionService.throwSystemException(DatasourceNotRegisteredException.class, cause, null);
        });
        assertEquals("Datasource not registered", exception.getMessage());
    }
}
