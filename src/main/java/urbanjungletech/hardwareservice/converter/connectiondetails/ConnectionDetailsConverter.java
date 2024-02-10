package urbanjungletech.hardwareservice.converter.connectiondetails;

import urbanjungletech.hardwareservice.entity.connectiondetails.ConnectionDetailsEntity;
import urbanjungletech.hardwareservice.model.connectiondetails.ConnectionDetails;

public interface ConnectionDetailsConverter {
    ConnectionDetails toModel(ConnectionDetailsEntity connectionDetailsEntity);
    void fillEntity(ConnectionDetailsEntity entity, ConnectionDetails model);

    ConnectionDetailsEntity createEntity(ConnectionDetails model);
}
