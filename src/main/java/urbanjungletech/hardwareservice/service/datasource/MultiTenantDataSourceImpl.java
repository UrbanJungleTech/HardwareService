package urbanjungletech.hardwareservice.service.datasource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.model.connectiondetails.DatabaseConnectionDetails;
import urbanjungletech.hardwareservice.model.credentials.DatabaseCredentials;

import javax.sql.DataSource;
import java.util.Map;

@Service
@Primary
public class MultiTenantDataSourceImpl extends AbstractRoutingDataSource implements MultiTenantDataSource{

    private final ThreadLocal<DatabaseConnectionDetails> dataSourceId;

    public MultiTenantDataSourceImpl(@Qualifier("dataSourceId") ThreadLocal<DatabaseConnectionDetails> dataSourceId,
                                     DataSource defaultDataSource,
                                     Map<Object, Object> targetDataSources) {
        this.dataSourceId = dataSourceId;
        this.setDefaultTargetDataSource(defaultDataSource);
        this.setTargetDataSources(targetDataSources);
    }

    @Override
    public ThreadLocal<DatabaseConnectionDetails> getDataSourceId() {
        return this.dataSourceId;
    }
    @Override
    protected Object determineCurrentLookupKey() {
        return this.dataSourceId.get();
    }
}
