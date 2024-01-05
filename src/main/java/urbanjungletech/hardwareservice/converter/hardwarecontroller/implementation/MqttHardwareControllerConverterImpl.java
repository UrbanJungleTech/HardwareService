package urbanjungletech.hardwareservice.converter.hardwarecontroller.implementation;

import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.converter.hardwarecontroller.HardwareMqttClientConverter;
import urbanjungletech.hardwareservice.converter.hardwarecontroller.SpecificHardwareControllerConverter;
import urbanjungletech.hardwareservice.entity.hardwarecontroller.MqttHardwareControllerEntity;
import urbanjungletech.hardwareservice.model.hardwarecontroller.HardwareMqttClient;
import urbanjungletech.hardwareservice.model.hardwarecontroller.MqttHardwareController;

import java.util.Optional;

@Service
public class MqttHardwareControllerConverterImpl implements SpecificHardwareControllerConverter<MqttHardwareController, MqttHardwareControllerEntity> {

    private final HardwareMqttClientConverter hardwareMqttClientConverter;

    private MqttHardwareControllerConverterImpl(HardwareMqttClientConverter hardwareMqttClientConverter) {
        this.hardwareMqttClientConverter = hardwareMqttClientConverter;
    }

    @Override
    public void fillEntity(MqttHardwareControllerEntity hardwareControllerEntity, MqttHardwareController hardwareController) {
        hardwareControllerEntity.setSerialNumber(hardwareController.getSerialNumber());
    }

    @Override
    public MqttHardwareController toModel(MqttHardwareControllerEntity hardwareControllerEntity) {
        MqttHardwareController result = new MqttHardwareController();
        Optional.ofNullable(hardwareControllerEntity.getHardwareMqttClient()).ifPresent((hardwareMqttClientEntity) -> {
            HardwareMqttClient hardwareMqttClient = hardwareMqttClientConverter.toModel(hardwareControllerEntity.getHardwareMqttClient());
            result.setHardwareMqttClient(hardwareMqttClient);
        });

        result.setSerialNumber(hardwareControllerEntity.getSerialNumber());
        return result;
    }

    @Override
    public MqttHardwareControllerEntity createEntity(MqttHardwareController hardwareController) {
        MqttHardwareControllerEntity result = new MqttHardwareControllerEntity();
        return result;
    }
}
