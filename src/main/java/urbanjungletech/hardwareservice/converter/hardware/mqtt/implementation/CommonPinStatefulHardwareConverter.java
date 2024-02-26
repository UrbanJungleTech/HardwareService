package urbanjungletech.hardwareservice.converter.hardware.mqtt.implementation;

import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.converter.hardware.SpecificHardwareConverter;
import urbanjungletech.hardwareservice.converter.hardware.mqtt.MqttPinConverter;
import urbanjungletech.hardwareservice.dao.MqttPinDAO;
import urbanjungletech.hardwareservice.entity.hardware.mqtt.CommonPinStatefulHardwareEntity;
import urbanjungletech.hardwareservice.model.hardware.mqtt.CommonPinStatefulHardware;

import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CommonPinStatefulHardwareConverter implements SpecificHardwareConverter<CommonPinStatefulHardware, CommonPinStatefulHardwareEntity> {


    private MqttPinConverter mqttPinConverter;
    private MqttPinDAO mqttPinDAO;

    public CommonPinStatefulHardwareConverter(MqttPinConverter mqttPinConverter,
                                              MqttPinDAO mqttPinDAO) {
        this.mqttPinConverter = mqttPinConverter;
        this.mqttPinDAO = mqttPinDAO;
    }
    @Override
    public CommonPinStatefulHardware toModel(CommonPinStatefulHardwareEntity hardwareEntity) {
        CommonPinStatefulHardware result = new CommonPinStatefulHardware();
        result.setId(hardwareEntity.getId());
        result.setPins(hardwareEntity.getPins().entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> this.mqttPinConverter.toModel(entry.getValue()))));
        result.setCommonPin(this.mqttPinConverter.toModel(hardwareEntity.getCommonPin()));
        return result;
    }

    @Override
    public CommonPinStatefulHardwareEntity createEntity(CommonPinStatefulHardware hardware) {
        CommonPinStatefulHardwareEntity result = new CommonPinStatefulHardwareEntity();
        return result;
    }

    @Override
    public void fillEntity(CommonPinStatefulHardwareEntity hardwareEntity, CommonPinStatefulHardware hardware) {
        hardwareEntity.setPins(hardware.getPins().entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> this.mqttPinDAO.findById(entry.getValue().getId()))));
        hardwareEntity.setCommonPin(this.mqttPinDAO.findById(hardware.getCommonPin().getId()));
    }
}
