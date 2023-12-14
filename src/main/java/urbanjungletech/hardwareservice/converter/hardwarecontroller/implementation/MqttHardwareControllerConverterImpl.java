package urbanjungletech.hardwareservice.converter.hardwarecontroller.implementation;

import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.converter.hardwarecontroller.SpecificHardwareControllerConverter;
import urbanjungletech.hardwareservice.entity.hardwarecontroller.MqttHardwareControllerEntity;
import urbanjungletech.hardwareservice.model.hardwarecontroller.MqttHardwareController;

@Service
public class MqttHardwareControllerConverterImpl implements SpecificHardwareControllerConverter<MqttHardwareController, MqttHardwareControllerEntity> {

    @Override
    public void fillEntity(MqttHardwareControllerEntity hardwareControllerEntity, MqttHardwareController hardwareController) {
        hardwareControllerEntity.setBrokerUrl(hardwareController.getBrokerUrl());
        hardwareControllerEntity.setResponseTopic(hardwareController.getResponseTopic());
        hardwareControllerEntity.setRequestTopic(hardwareController.getRequestTopic());
        hardwareControllerEntity.setSerialNumber(hardwareController.getSerialNumber());
    }

    @Override
    public MqttHardwareController toModel(MqttHardwareControllerEntity hardwareControllerEntity) {
        MqttHardwareController result = new MqttHardwareController();
        result.setBrokerUrl(hardwareControllerEntity.getBrokerUrl());
        result.setResponseTopic(hardwareControllerEntity.getResponseTopic());
        result.setRequestTopic(hardwareControllerEntity.getRequestTopic());
        result.setSerialNumber(hardwareControllerEntity.getSerialNumber());
        return result;
    }

    @Override
    public MqttHardwareControllerEntity createEntity(MqttHardwareController hardwareController) {
        MqttHardwareControllerEntity result = new MqttHardwareControllerEntity();
        return result;
    }
}
