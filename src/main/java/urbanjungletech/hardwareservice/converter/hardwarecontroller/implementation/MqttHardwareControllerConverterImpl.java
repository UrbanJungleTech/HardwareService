package urbanjungletech.hardwareservice.converter.hardwarecontroller.implementation;

import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.converter.hardwarecontroller.SpecificHardwareControllerConverter;
import urbanjungletech.hardwareservice.entity.hardwarecontroller.MqttHardwareControllerEntity;
import urbanjungletech.hardwareservice.model.hardwarecontroller.MqttHardwareController;

@Service
public class MqttHardwareControllerConverterImpl implements SpecificHardwareControllerConverter<MqttHardwareController, MqttHardwareControllerEntity> {

    @Override
    public void fillEntity(MqttHardwareControllerEntity hardwareControllerEntity, MqttHardwareController hardwareController) {

    }

    @Override
    public MqttHardwareController toModel(MqttHardwareControllerEntity hardwareControllerEntity) {
        MqttHardwareController result = new MqttHardwareController();
        return result;
    }

    @Override
    public MqttHardwareControllerEntity createEntity(MqttHardwareController hardwareController) {
        MqttHardwareControllerEntity result = new MqttHardwareControllerEntity();
        return result;
    }
}
