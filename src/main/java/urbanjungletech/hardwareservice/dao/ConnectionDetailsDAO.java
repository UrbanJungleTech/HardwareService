package urbanjungletech.hardwareservice.dao;

import urbanjungletech.hardwareservice.entity.connectiondetails.ConnectionDetailsEntity;
import urbanjungletech.hardwareservice.model.connectiondetails.ConnectionDetails;

public interface ConnectionDetailsDAO {
    ConnectionDetailsEntity create(ConnectionDetails connectionDetails);
    ConnectionDetailsEntity update(ConnectionDetails connectionDetails);
    void delete(long id);
}
