package frentz.daniel.hardwareservice.exception;

import java.io.IOException;

public class MqttMockServerStartupException extends RuntimeException {
    public MqttMockServerStartupException(IOException e) {
        super(e);
    }
}
