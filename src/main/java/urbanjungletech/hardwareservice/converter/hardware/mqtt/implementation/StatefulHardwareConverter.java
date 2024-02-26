package urbanjungletech.hardwareservice.converter.hardware.mqtt.implementation;

import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.converter.hardware.SpecificHardwareConverter;
import urbanjungletech.hardwareservice.converter.hardware.mqtt.MqttPinConverter;
import urbanjungletech.hardwareservice.dao.MqttPinDAO;
import urbanjungletech.hardwareservice.entity.hardware.mqtt.StatefulHardwareEntity;
import urbanjungletech.hardwareservice.model.hardware.mqtt.StatefulHardware;

import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StatefulHardwareConverter implements SpecificHardwareConverter<StatefulHardware, StatefulHardwareEntity> {
    private final MqttPinConverter mqttPinConverter;
    private final MqttPinDAO mqttPinDAO;

    public StatefulHardwareConverter(MqttPinConverter mqttPinConverter,
                                     MqttPinDAO mqttPinDAO) {
        this.mqttPinConverter = mqttPinConverter;
        this.mqttPinDAO = mqttPinDAO;
    }
    @Override
    public StatefulHardware toModel(StatefulHardwareEntity hardwareEntity) {
        StatefulHardware result = new StatefulHardware();
        result.setId(hardwareEntity.getId());
        result.setPins(hardwareEntity.getPins().entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> this.mqttPinConverter.toModel(entry.getValue()))));
        return result;
    }

    @Override
    public StatefulHardwareEntity createEntity(StatefulHardware hardware) {
        StatefulHardwareEntity result = new StatefulHardwareEntity();
        return result;
    }

    @Override
    public void fillEntity(StatefulHardwareEntity hardwareEntity, StatefulHardware hardware) {
        hardwareEntity.setPins(hardware.getPins().entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> this.mqttPinDAO.findById(entry.getValue().getId()))));
    }
}
