package urbanjungletech.hardwareservice.service.datasource;

import urbanjungletech.hardwareservice.model.connectiondetails.ConnectionDetails;
import urbanjungletech.hardwareservice.model.connectiondetails.DatabaseConnectionDetails;
import urbanjungletech.hardwareservice.model.credentials.DatabaseCredentials;

import javax.sql.DataSource;
import java.util.Map;

public interface MultiTenantDataSource {
    ThreadLocal<DatabaseConnectionDetails> getDataSourceId();

    void setTargetDataSources(Map<Object, Object> dataSources);

    void initialize();
}
