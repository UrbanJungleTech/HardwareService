package urbanjungletech.hardwareservice.entity.connectiondetails;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import urbanjungletech.hardwareservice.entity.credentials.CredentialsEntity;

@Entity
public class DatabaseConnectionDetailsEntity extends ConnectionDetailsEntity{
    @ManyToOne
    private CredentialsEntity credentials;
    private String url;
    private long port;
    private String database;

    public CredentialsEntity getCredentials() {
        return credentials;
    }

    public void setCredentials(CredentialsEntity credentials) {
        this.credentials = credentials;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String databaseName) {
        this.database = databaseName;
    }

    public long getPort() {
        return port;
    }

    public void setPort(long port) {
        this.port = port;
    }
}
