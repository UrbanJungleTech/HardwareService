package urbanjungletech.hardwareservice.converter.hardwarecontroller.implementation;

import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.converter.hardwarecontroller.HardwareMqttClientConverter;
import urbanjungletech.hardwareservice.entity.hardwarecontroller.HardwareMqttClientEntity;
import urbanjungletech.hardwareservice.model.hardwarecontroller.HardwareMqttClient;

@Service
public class HardwareMqttClientConverterImpl implements HardwareMqttClientConverter {

    @Override
    public void fillEntity(HardwareMqttClientEntity hardwareMqttClientEntity, HardwareMqttClient hardwareMqttClient) {
        hardwareMqttClientEntity.setBrokerUrl(hardwareMqttClient.getBrokerUrl());
        hardwareMqttClientEntity.setRequestTopic(hardwareMqttClient.getRequestTopic());
        hardwareMqttClientEntity.setResponseTopic(hardwareMqttClient.getResponseTopic());
    }

    @Override
    public HardwareMqttClient toModel(HardwareMqttClientEntity hardwareMqttClientEntity) {
        HardwareMqttClient result = new HardwareMqttClient();
        result.setBrokerUrl(hardwareMqttClientEntity.getBrokerUrl());
        result.setRequestTopic(hardwareMqttClientEntity.getRequestTopic());
        result.setResponseTopic(hardwareMqttClientEntity.getResponseTopic());
        return result;
    }
}
