package urbanjungletech.hardwareservice.converter.connectiondetails;

import urbanjungletech.hardwareservice.entity.connectiondetails.ConnectionDetailsEntity;
import urbanjungletech.hardwareservice.model.connectiondetails.ConnectionDetails;

public interface SpecificConnectionDetailsConverter <ConnectionDetailsType extends ConnectionDetails,
        ConnectionDetailsEntityType extends ConnectionDetailsEntity>{
    ConnectionDetailsType toModel(ConnectionDetailsEntityType entity);
    ConnectionDetailsEntityType createEntity(ConnectionDetailsType connectionDetails);
    void fillEntity(ConnectionDetailsEntityType entity, ConnectionDetailsType connectionDetails);
}
