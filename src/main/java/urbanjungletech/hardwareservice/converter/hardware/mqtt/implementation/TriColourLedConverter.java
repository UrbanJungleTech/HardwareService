package urbanjungletech.hardwareservice.converter.hardware.mqtt.implementation;

import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.converter.hardware.SpecificHardwareConverter;
import urbanjungletech.hardwareservice.converter.hardware.mqtt.MqttPinConverter;
import urbanjungletech.hardwareservice.entity.hardware.mqtt.MqttPinEntity;
import urbanjungletech.hardwareservice.entity.hardware.mqtt.TriColourLedEntity;
import urbanjungletech.hardwareservice.model.hardware.mqtt.TriColourLed;
import urbanjungletech.hardwareservice.repository.MqttPinRepository;

@Service
public class TriColourLedConverter implements SpecificHardwareConverter<TriColourLed, TriColourLedEntity> {

    private final MqttPinConverter mqttPinConverter;
    private final MqttPinRepository mqttPinRepository;

    public TriColourLedConverter(MqttPinConverter mqttPinConverter,
                                 MqttPinRepository mqttPinRepository) {
        this.mqttPinConverter = mqttPinConverter;
        this.mqttPinRepository = mqttPinRepository;
    }
    @Override
    public TriColourLed toModel(TriColourLedEntity hardware) {
        TriColourLed result = new TriColourLed();
        result.setRedPin(this.mqttPinConverter.toModel(hardware.getRedPin()));
        result.setGreenPin(this.mqttPinConverter.toModel(hardware.getGreenPin()));
        result.setBluePin(this.mqttPinConverter.toModel(hardware.getBluePin()));
        result.setId(hardware.getId());
        return result;
    }

    @Override
    public TriColourLedEntity createEntity(TriColourLed hardware) {
        TriColourLedEntity result = new TriColourLedEntity();
        return result;
    }

    @Override
    public void fillEntity(TriColourLedEntity hardwareEntity, TriColourLed hardware) {
        MqttPinEntity redPinEntity = this.mqttPinRepository.findById(hardware.getRedPin().getId())
                .orElseGet(() -> this.mqttPinConverter.createEntity(hardware.getRedPin()));
        hardwareEntity.setRedPin(redPinEntity);
        MqttPinEntity greenPinEntity = this.mqttPinRepository.findById(hardware.getGreenPin().getId())
                .orElseGet(() -> this.mqttPinConverter.createEntity(hardware.getGreenPin()));
        hardwareEntity.setGreenPin(greenPinEntity);
        MqttPinEntity bluePinEntity = this.mqttPinRepository.findById(hardware.getBluePin().getId())
                .orElseGet(() -> this.mqttPinConverter.createEntity(hardware.getBluePin()));
        hardwareEntity.setBluePin(bluePinEntity);
    }
}
