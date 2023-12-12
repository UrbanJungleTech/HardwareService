package urbanjungletech.hardwareservice.service.datasource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.exception.exception.DatasourceNotRegisteredException;
import urbanjungletech.hardwareservice.model.credentials.DatabaseCredentials;
import urbanjungletech.hardwareservice.service.credentials.generator.implementation.DatasourceClientGenerator;

import javax.sql.DataSource;
import java.util.Map;

@Service
public class DataSourceContextImpl implements DataSourceContext{

    private Logger logger = LoggerFactory.getLogger(DataSourceContextImpl.class);
    private final Map<Object, Object> dataSources;
    private final MultiTenantDataSource multiTenantDataSource;
    private final DatasourceClientGenerator datasourceClientGenerator;

    public DataSourceContextImpl(@Qualifier("targetDataSources") Map<Object, Object> dataSources,
                                 MultiTenantDataSourceImpl multiTenantDataSource,
                                 DatasourceClientGenerator datasourceClientGenerator) {
        this.dataSources = dataSources;
        this.multiTenantDataSource = multiTenantDataSource;
        this.datasourceClientGenerator = datasourceClientGenerator;
    }

    @Override
    public void addDataSource(DatabaseCredentials credentials) {
        if(!this.dataSources.containsKey(credentials)) {
            logger.debug("Adding datasource for credentials: {}", credentials);
            DataSource dataSource = this.datasourceClientGenerator.generateClient(credentials);
            this.dataSources.put(credentials, dataSource);
            this.multiTenantDataSource.setTargetDataSources(this.dataSources);
            this.multiTenantDataSource.initialize();
        }
    }

    @Override
    public void removeDataSource(DatabaseCredentials credentials) {
        if(this.dataSources.containsKey(credentials)) {
            logger.debug("Removing datasource for credentials: {}", credentials);
            this.dataSources.remove(credentials);
            this.multiTenantDataSource.setTargetDataSources(this.dataSources);
            this.multiTenantDataSource.initialize();
        }
    }

    @Override
    public void setDataSource(DatabaseCredentials credentials) {
        if(this.dataSources.containsKey(credentials)) {
            logger.debug("Setting datasource for credentials: {}", credentials);
            this.multiTenantDataSource.getDataSourceId().set(credentials);
        }
        else{
            logger.error("Datasource not registered for credentials: {}", credentials);
            throw new DatasourceNotRegisteredException();
        }
    }
}
