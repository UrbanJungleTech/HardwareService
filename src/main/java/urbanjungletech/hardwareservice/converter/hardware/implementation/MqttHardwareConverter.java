package urbanjungletech.hardwareservice.converter.hardware.implementation;

import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.converter.hardware.SpecificHardwareConverter;
import urbanjungletech.hardwareservice.entity.hardware.HardwareEntity;
import urbanjungletech.hardwareservice.entity.hardware.MqttHardwareEntity;
import urbanjungletech.hardwareservice.model.hardware.Hardware;
import urbanjungletech.hardwareservice.model.hardware.MqttHardware;

@Service
public class MqttHardwareConverter implements SpecificHardwareConverter<MqttHardware, MqttHardwareEntity> {
    @Override
    public MqttHardware toModel(MqttHardwareEntity hardware) {
        MqttHardware mqttHardware = new MqttHardware();
        return mqttHardware;
    }

    @Override
    public MqttHardwareEntity createEntity(MqttHardware hardware) {
        return new MqttHardwareEntity();
    }

    @Override
    public void fillEntity(MqttHardwareEntity hardwareEntity, MqttHardware hardware) {

    }
}
