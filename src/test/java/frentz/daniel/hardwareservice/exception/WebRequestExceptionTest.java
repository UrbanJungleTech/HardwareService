package frentz.daniel.hardwareservice.exception;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class WebRequestExceptionTest {


    @Test
    void setMessage() {
        WebRequestException webRequestException = new WebRequestException();
        String message = "error message";
        webRequestException.setMessage(message);
        assertEquals(message, webRequestException.getMessage());
    }

    @Test
    void setHttpStatus() {
        WebRequestException webRequestException = new WebRequestException();
        int httpStatus = 500;
        webRequestException.setHttpStatus(httpStatus);
        assertEquals(httpStatus, webRequestException.getHttpStatus());
    }
}
