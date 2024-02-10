package urbanjungletech.hardwareservice.model.connectiondetails;

import urbanjungletech.hardwareservice.model.credentials.Credentials;

public class DatabaseConnectionDetails extends ConnectionDetails{
    private String url;
    private long port;
    private String Driver;
    private String database;
    private String dialect;
    private Credentials credentials;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDriver() {
        return Driver;
    }

    public void setDriver(String driver) {
        Driver = driver;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public long getPort() {
        return port;
    }

    public void setPort(long port) {
        this.port = port;
    }

    public String getDialect() {
        return dialect;
    }

    public void setDialect(String dialect) {
        this.dialect = dialect;
    }

    public Credentials getCredentials() {
        return credentials;
    }

    public void setCredentials(Credentials credentials) {
        this.credentials = credentials;
    }
}
