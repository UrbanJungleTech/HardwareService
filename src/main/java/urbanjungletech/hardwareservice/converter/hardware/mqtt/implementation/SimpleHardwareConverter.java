package urbanjungletech.hardwareservice.converter.hardware.mqtt.implementation;

import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.converter.hardware.SpecificHardwareConverter;
import urbanjungletech.hardwareservice.converter.hardware.mqtt.MqttPinConverter;
import urbanjungletech.hardwareservice.entity.hardware.mqtt.MqttPinEntity;
import urbanjungletech.hardwareservice.entity.hardware.mqtt.SimpleHardwareEntity;
import urbanjungletech.hardwareservice.model.hardware.mqtt.MqttPin;
import urbanjungletech.hardwareservice.model.hardware.mqtt.SimpleHardware;
import urbanjungletech.hardwareservice.repository.MqttPinRepository;


@Service
public class SimpleHardwareConverter implements SpecificHardwareConverter<SimpleHardware, SimpleHardwareEntity> {

    private final MqttPinConverter mqttPinConverter;
    private final MqttPinRepository mqttPinRepository;

    public SimpleHardwareConverter(MqttPinConverter mqttPinConverter,
                                   MqttPinRepository mqttPinRepository) {
        this.mqttPinConverter = mqttPinConverter;
        this.mqttPinRepository = mqttPinRepository;
    }
    @Override
    public SimpleHardware toModel(SimpleHardwareEntity simpleHardwareEntity) {
        SimpleHardware result = new SimpleHardware();
        MqttPin mqttPin = this.mqttPinConverter.toModel(simpleHardwareEntity.getPin());
        result.setPin(mqttPin);
        return result;
    }

    @Override
    public SimpleHardwareEntity createEntity(SimpleHardware hardware) {
        SimpleHardwareEntity result = new SimpleHardwareEntity();
        return result;
    }

    @Override
    public void fillEntity(SimpleHardwareEntity hardwareEntity, SimpleHardware hardware) {
        MqttPinEntity mqttPinEntity = this.mqttPinRepository.findById(hardware.getPin().getId())
                .orElseGet(() -> this.mqttPinConverter.createEntity(hardware.getPin()));
        hardwareEntity.setPin(mqttPinEntity);
    }
}
