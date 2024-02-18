package urbanjungletech.hardwareservice.service.clientgenerator;

import urbanjungletech.hardwareservice.model.connectiondetails.DatabaseConnectionDetails;

import javax.sql.DataSource;

public interface DatasourceClientGenerator {
    DataSource generateClient(DatabaseConnectionDetails connectionDetails);
}
