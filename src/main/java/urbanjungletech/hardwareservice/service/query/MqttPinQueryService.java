package urbanjungletech.hardwareservice.service.query;

import urbanjungletech.hardwareservice.model.hardware.mqtt.MqttPin;

public interface MqttPinQueryService {
    MqttPin getPinById(Long id);
}
