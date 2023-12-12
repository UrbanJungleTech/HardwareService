package urbanjungletech.hardwareservice.service.datasource;

import urbanjungletech.hardwareservice.model.credentials.DatabaseCredentials;

import javax.sql.DataSource;
import java.util.Map;

public interface MultiTenantDataSource {
    ThreadLocal<DatabaseCredentials> getDataSourceId();

    void setTargetDataSources(Map<Object, Object> dataSources);

    void initialize();
}
