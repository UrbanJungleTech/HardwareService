package urbanjungletech.hardwareservice.converter.connectiondetails.implementation;

import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.converter.connectiondetails.ConnectionDetailsConverter;
import urbanjungletech.hardwareservice.converter.connectiondetails.SpecificConnectionDetailsConverter;
import urbanjungletech.hardwareservice.entity.connectiondetails.ConnectionDetailsEntity;
import urbanjungletech.hardwareservice.model.connectiondetails.ConnectionDetails;

import java.util.Map;

@Service
public class ConnectionDetailsConverterImpl implements ConnectionDetailsConverter {

    private final Map<Class, SpecificConnectionDetailsConverter> connectionDetailsConverterMap;

    public ConnectionDetailsConverterImpl(Map<Class, SpecificConnectionDetailsConverter> connectionDetailsConverterMap) {
        this.connectionDetailsConverterMap = connectionDetailsConverterMap;
    }
    @Override
    public ConnectionDetails toModel(ConnectionDetailsEntity connectionDetailsEntity) {
        ConnectionDetails connectionDetails = this.connectionDetailsConverterMap.get(connectionDetailsEntity.getClass()).toModel(connectionDetailsEntity);
        connectionDetails.setId(connectionDetailsEntity.getId());
        return connectionDetails;
    }

    @Override
    public void fillEntity(ConnectionDetailsEntity entity, ConnectionDetails model) {
        this.connectionDetailsConverterMap.get(model.getClass()).fillEntity(entity, model);
    }

    @Override
    public ConnectionDetailsEntity createEntity(ConnectionDetails model) {
        ConnectionDetailsEntity connectionDetailsEntity = this.connectionDetailsConverterMap.get(model.getClass()).createEntity(model);
        this.fillEntity(connectionDetailsEntity, model);
        return connectionDetailsEntity;
    }
}
