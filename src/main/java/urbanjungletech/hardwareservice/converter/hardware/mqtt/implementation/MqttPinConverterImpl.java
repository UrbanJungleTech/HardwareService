package urbanjungletech.hardwareservice.converter.hardware.mqtt.implementation;

import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.converter.hardware.mqtt.MqttPinConverter;
import urbanjungletech.hardwareservice.entity.hardware.mqtt.MqttPinEntity;
import urbanjungletech.hardwareservice.model.hardware.mqtt.MqttPin;

@Service
public class MqttPinConverterImpl implements MqttPinConverter {

    @Override
    public MqttPin toModel(MqttPinEntity mqttPinEntity) {
        MqttPin result = new MqttPin();
        result.setLevelable(mqttPinEntity.getLevelable());
        result.setPin(mqttPinEntity.getPin());
        result.setId(mqttPinEntity.getId());
        return result;
    }

    @Override
    public MqttPinEntity createEntity(MqttPin mqttPin) {
        MqttPinEntity result = new MqttPinEntity();
        return result;
    }

    @Override
    public void fillEntity(MqttPinEntity hardwareEntity, MqttPin hardware) {
        hardwareEntity.setLevelable(hardware.isLevelable());
        hardwareEntity.setPin(hardware.getPin());
    }
}
