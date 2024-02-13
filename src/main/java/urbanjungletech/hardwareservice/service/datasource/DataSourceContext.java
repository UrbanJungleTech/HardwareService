package urbanjungletech.hardwareservice.service.datasource;

import urbanjungletech.hardwareservice.model.connectiondetails.DatabaseConnectionDetails;

public interface DataSourceContext {
    void addDataSource(DatabaseConnectionDetails connectionDetails);

    void removeDataSource(DatabaseConnectionDetails connectionDetails);

    void setDataSource(DatabaseConnectionDetails connectionDetails);
}
