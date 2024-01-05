package urbanjungletech.hardwareservice.exception;

import urbanjungletech.hardwareservice.model.hardwarecontroller.MqttHardwareController;

public class MqttCredentialsServiceNotFoundException extends RuntimeException {
    public MqttCredentialsServiceNotFoundException(MqttHardwareController hardwareController) {
    }
}
