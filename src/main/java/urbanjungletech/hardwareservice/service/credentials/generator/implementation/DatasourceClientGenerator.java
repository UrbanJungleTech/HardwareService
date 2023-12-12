package urbanjungletech.hardwareservice.service.credentials.generator.implementation;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import net.sf.cglib.proxy.Enhancer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.cfg.Configuration;
import org.hibernate.mapping.Table;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.converter.SensorReadingConverter;
import urbanjungletech.hardwareservice.entity.SensorReadingEntity;
import urbanjungletech.hardwareservice.model.credentials.Credentials;
import urbanjungletech.hardwareservice.model.credentials.DatabaseCredentials;
import urbanjungletech.hardwareservice.service.credentials.generator.SpecificClientGenerator;
import urbanjungletech.hardwareservice.service.credentials.retrieval.CredentialsRetrievalService;

import javax.sql.DataSource;
import java.net.URI;
import java.util.Map;
import java.util.Properties;

@Service
public class DatasourceClientGenerator implements SpecificClientGenerator<DataSource, DatabaseCredentials> {

    private final Map<Long, SessionFactory> sessionFactoryCache;
    private final CredentialsRetrievalService credentialsRetrievalService;

    public DatasourceClientGenerator(Map<Long, SessionFactory> sessionFactoryCache,
                                     CredentialsRetrievalService credentialsRetrievalService) {
        this.sessionFactoryCache = sessionFactoryCache;
        this.credentialsRetrievalService = credentialsRetrievalService;
    }
    @Override
    public DataSource generateClient(DatabaseCredentials credentials) {
        credentials = (DatabaseCredentials)this.credentialsRetrievalService.getCredentials(credentials);

        DataSource dataSource = DataSourceBuilder.create()
            .url(credentials.getHost())
            .username(credentials.getUsername())
            .password(credentials.getPassword())
            .driverClassName(credentials.getDriver())
            .build();
        return dataSource;
    }
}
