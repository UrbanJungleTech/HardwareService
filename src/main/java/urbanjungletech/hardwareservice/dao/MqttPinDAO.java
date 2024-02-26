package urbanjungletech.hardwareservice.dao;

import urbanjungletech.hardwareservice.entity.hardware.mqtt.MqttPinEntity;
import urbanjungletech.hardwareservice.model.hardware.mqtt.MqttPin;

public interface MqttPinDAO {
    MqttPinEntity createMqttPin(MqttPin mqttPin);
    void deleteMqttPin(Long id);
    MqttPinEntity updateMqttPin(MqttPin mqttPin);
    MqttPinEntity findById(Long id);
}
