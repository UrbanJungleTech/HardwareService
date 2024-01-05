package urbanjungletech.hardwareservice.converter.hardwarecontroller;

import urbanjungletech.hardwareservice.entity.hardwarecontroller.HardwareMqttClientEntity;
import urbanjungletech.hardwareservice.model.hardwarecontroller.HardwareMqttClient;

public interface HardwareMqttClientConverter {
    void fillEntity(HardwareMqttClientEntity hardwareMqttClientEntity, HardwareMqttClient hardwareMqttClient);

    HardwareMqttClient toModel(HardwareMqttClientEntity hardwareMqttClientEntity);
}
