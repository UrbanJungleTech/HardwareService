package frentz.daniel.hardwareservice.service;

import frentz.daniel.hardwareservice.exception.NameNotFoundException;
import frentz.daniel.hardwareservice.exception.StandardNotFoundException;
import frentz.daniel.hardwareservice.service.implementation.ExceptionServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ExceptionServiceImplTest {

    @Mock
    EntityNameService entityNameService;
    @InjectMocks
    private ExceptionServiceImpl exceptionService;
    @Test
    void createNotFoundException_shouldReturnAStandardNotFoundExceptionWhenClassIsRegistered() {
        long expectedId = 1L;
        HttpStatus expectedStatus = HttpStatus.NOT_FOUND;
        String expectedName = "Object";
        String expectedMessage = "Object not found with id of 1";

        when(entityNameService.getName(eq(Object.class))).thenReturn(expectedName);

        StandardNotFoundException result = this.exceptionService.createNotFoundException(Object.class, expectedId);

        assertEquals(expectedStatus, result.getStatus());
        assertEquals(expectedMessage, result.getMessage());
    }

    @Test
    void createNotFoundException_shouldThrowNameNotFoundWhenClassIsNotRegisteredWithNameService() {
        long expectedId = 1L;

        when(entityNameService.getName(eq(Object.class))).thenThrow(NameNotFoundException.class);

        assertThrows(NameNotFoundException.class, () -> this.exceptionService.createNotFoundException(Object.class, expectedId));
    }
}
