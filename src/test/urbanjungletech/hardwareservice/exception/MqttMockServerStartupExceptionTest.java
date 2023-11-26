package urbanjungletech.hardwareservice.exception;

import org.junit.jupiter.api.Test;
import urbanjungletech.hardwareservice.exception.exception.MqttMockServerStartupException;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class MqttMockServerStartupExceptionTest {

    @Test
    void constructor() {
        IOException ioException = new IOException();
        MqttMockServerStartupException mqttMockServerStartupException = new MqttMockServerStartupException(ioException);
        assertNotNull(mqttMockServerStartupException);
        assertSame(ioException, mqttMockServerStartupException.getCause());
    }

}
