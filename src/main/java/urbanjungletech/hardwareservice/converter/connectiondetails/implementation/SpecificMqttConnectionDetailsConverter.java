package urbanjungletech.hardwareservice.converter.connectiondetails.implementation;

import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.converter.connectiondetails.SpecificConnectionDetailsConverter;
import urbanjungletech.hardwareservice.converter.credentials.CredentialsConverter;
import urbanjungletech.hardwareservice.entity.connectiondetails.MqttConnectionDetailsEntity;
import urbanjungletech.hardwareservice.model.connectiondetails.MqttConnectionDetails;

@Service
public class SpecificMqttConnectionDetailsConverter implements SpecificConnectionDetailsConverter<MqttConnectionDetails, MqttConnectionDetailsEntity> {

    private final CredentialsConverter credentialsConverter;

    public SpecificMqttConnectionDetailsConverter(CredentialsConverter credentialsConverter) {
        this.credentialsConverter = credentialsConverter;
    }
    @Override
    public MqttConnectionDetails toModel(MqttConnectionDetailsEntity entity) {
        MqttConnectionDetails result = new MqttConnectionDetails();
        result.setClientId(entity.getClientId());
        result.setBroker(entity.getBroker());
        return result;
    }

    @Override
    public MqttConnectionDetailsEntity createEntity(MqttConnectionDetails connectionDetails) {
        MqttConnectionDetailsEntity result = new MqttConnectionDetailsEntity();
        fillEntity(result, connectionDetails);
        return result;
    }

    @Override
    public void fillEntity(MqttConnectionDetailsEntity entity, MqttConnectionDetails connectionDetails) {
        entity.setClientId(connectionDetails.getClientId());
        entity.setBroker(connectionDetails.getBroker());
        this.credentialsConverter.fillEntity(entity.getCredentials(), connectionDetails.getCredentials());
    }
}
