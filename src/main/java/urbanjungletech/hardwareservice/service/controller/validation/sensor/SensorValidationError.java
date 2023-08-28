package urbanjungletech.hardwareservice.service.controller.validation.sensor;

import java.util.ArrayList;
import java.util.List;

public class SensorValidationError {
    private List<String> messages;

    public SensorValidationError() {
        this.messages = new ArrayList<>();
    }

    public List<String> getMessages() {
        return messages;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }
}
