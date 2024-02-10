package urbanjungletech.hardwareservice.service.datasource;

import jakarta.activation.DataSource;
import urbanjungletech.hardwareservice.model.connectiondetails.DatabaseConnectionDetails;
import urbanjungletech.hardwareservice.model.credentials.DatabaseCredentials;

public interface DataSourceContext {
    void addDataSource(DatabaseConnectionDetails connectionDetails);

    void removeDataSource(DatabaseConnectionDetails connectionDetails);

    void setDataSource(DatabaseConnectionDetails connectionDetails);
}
