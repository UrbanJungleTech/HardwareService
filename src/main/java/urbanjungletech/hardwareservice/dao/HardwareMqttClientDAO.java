package urbanjungletech.hardwareservice.dao;

import urbanjungletech.hardwareservice.entity.hardwarecontroller.HardwareMqttClientEntity;
import urbanjungletech.hardwareservice.model.hardwarecontroller.HardwareMqttClient;

public interface HardwareMqttClientDAO {
    HardwareMqttClientEntity create(HardwareMqttClient hardwareMqttClient);
    void delete(long id);
    HardwareMqttClientEntity update(HardwareMqttClient hardwareMqttClient);
}
