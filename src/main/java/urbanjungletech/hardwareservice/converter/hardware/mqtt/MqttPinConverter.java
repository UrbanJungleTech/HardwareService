package urbanjungletech.hardwareservice.converter.hardware.mqtt;

import urbanjungletech.hardwareservice.entity.hardware.mqtt.MqttPinEntity;
import urbanjungletech.hardwareservice.model.hardware.mqtt.MqttPin;

public interface MqttPinConverter {
    MqttPin toModel(MqttPinEntity mqttPinEntity);

    MqttPinEntity createEntity(MqttPin mqttPin);

    void fillEntity(MqttPinEntity hardwareEntity, MqttPin hardware);
}
