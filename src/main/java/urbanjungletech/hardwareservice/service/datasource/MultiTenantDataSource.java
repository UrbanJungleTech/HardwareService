package urbanjungletech.hardwareservice.service.datasource;

import urbanjungletech.hardwareservice.model.connectiondetails.DatabaseConnectionDetails;

import java.util.Map;

public interface MultiTenantDataSource {
    ThreadLocal<DatabaseConnectionDetails> getDataSourceId();

    void setTargetDataSources(Map<Object, Object> dataSources);

    void initialize();
}
