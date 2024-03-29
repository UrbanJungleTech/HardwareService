package urbanjungletech.hardwareservice.service.clientgenerator.implementation;

import org.hibernate.SessionFactory;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.model.connectiondetails.DatabaseConnectionDetails;
import urbanjungletech.hardwareservice.model.credentials.DatabaseCredentials;
import urbanjungletech.hardwareservice.service.clientgenerator.DatasourceClientGenerator;
import urbanjungletech.hardwareservice.service.credentials.retrieval.CredentialsRetrievalService;

import javax.sql.DataSource;
import java.util.Map;

@Service
public class DatasourceClientGeneratorImpl implements DatasourceClientGenerator {

    private final Map<Long, SessionFactory> sessionFactoryCache;
    private final CredentialsRetrievalService credentialsRetrievalService;

    public DatasourceClientGeneratorImpl(Map<Long, SessionFactory> sessionFactoryCache,
                                     CredentialsRetrievalService credentialsRetrievalService) {
        this.sessionFactoryCache = sessionFactoryCache;
        this.credentialsRetrievalService = credentialsRetrievalService;
    }
    @Override
    public DataSource generateClient(DatabaseConnectionDetails connectionDetails) {
        DatabaseCredentials credentials = (DatabaseCredentials)this.credentialsRetrievalService.getCredentials(connectionDetails.getCredentials());

        DataSource dataSource = DataSourceBuilder.create()
            .url(connectionDetails.getUrl())
            .username(credentials.getUsername())
            .password(credentials.getPassword())
            .driverClassName(connectionDetails.getDriver())
            .build();
        return dataSource;
    }
}
