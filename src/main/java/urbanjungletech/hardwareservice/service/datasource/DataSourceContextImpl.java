package urbanjungletech.hardwareservice.service.datasource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.exception.exception.DatasourceNotRegisteredException;
import urbanjungletech.hardwareservice.exception.service.ExceptionService;
import urbanjungletech.hardwareservice.model.connectiondetails.DatabaseConnectionDetails;
import urbanjungletech.hardwareservice.model.credentials.DatabaseCredentials;
import urbanjungletech.hardwareservice.service.client.generator.implementation.DatasourceClientGenerator;

import javax.sql.DataSource;
import java.util.Map;

@Service
public class DataSourceContextImpl implements DataSourceContext{

    private Logger logger = LoggerFactory.getLogger(DataSourceContextImpl.class);
    private final Map<Object, Object> dataSources;
    private final MultiTenantDataSource multiTenantDataSource;
    private final DatasourceClientGenerator datasourceClientGenerator;
    private final ExceptionService exceptionService;

    public DataSourceContextImpl(@Qualifier("targetDataSources") Map<Object, Object> dataSources,
                                 MultiTenantDataSourceImpl multiTenantDataSource,
                                 DatasourceClientGenerator datasourceClientGenerator,
                                 ExceptionService exceptionService) {
        this.dataSources = dataSources;
        this.multiTenantDataSource = multiTenantDataSource;
        this.datasourceClientGenerator = datasourceClientGenerator;
        this.exceptionService = exceptionService;
    }

    @Override
    public void addDataSource(DatabaseConnectionDetails connectionDetails) {
        if(!this.dataSources.containsKey(connectionDetails)) {
            logger.debug("Adding datasource for credentials: {}", connectionDetails);
            DataSource dataSource = this.datasourceClientGenerator.generateClient(connectionDetails);
            this.dataSources.put(connectionDetails, dataSource);
            this.multiTenantDataSource.setTargetDataSources(this.dataSources);
            this.multiTenantDataSource.initialize();
        }
    }

    @Override
    public void removeDataSource(DatabaseConnectionDetails connectionDetails) {
        if(this.dataSources.containsKey(connectionDetails)) {
            logger.debug("Removing datasource for credentials: {}", connectionDetails);
            this.dataSources.remove(connectionDetails);
            this.multiTenantDataSource.setTargetDataSources(this.dataSources);
            this.multiTenantDataSource.initialize();
        }
    }

    @Override
    public void setDataSource(DatabaseConnectionDetails connectionDetails) {
        if(this.dataSources.containsKey(connectionDetails)) {
            logger.debug("Setting datasource for credentials: {}", connectionDetails);
            this.multiTenantDataSource.getDataSourceId().set(connectionDetails);
        }
        else{
            logger.error("Datasource not registered for credentials: {}", connectionDetails);
            this.exceptionService.throwSystemException(DatasourceNotRegisteredException.class);
        }
    }
}
