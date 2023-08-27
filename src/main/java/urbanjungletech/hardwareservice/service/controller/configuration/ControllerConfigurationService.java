package urbanjungletech.hardwareservice.service.controller.configuration;

import org.eclipse.paho.client.mqttv3.MqttException;
import urbanjungletech.hardwareservice.model.HardwareController;
import urbanjungletech.hardwareservice.service.controller.controllercommunication.implementation.HardwareControllerCommunicationService;

import java.util.Map;

public interface ControllerConfigurationService {
    void configureController(HardwareController hardwareController);
}
