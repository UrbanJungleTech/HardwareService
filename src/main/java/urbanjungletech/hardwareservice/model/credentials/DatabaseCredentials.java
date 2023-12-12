package urbanjungletech.hardwareservice.model.credentials;

import java.util.Objects;

public class DatabaseCredentials extends Credentials{
    private String host;
    private String port;
    private String database;
    private String username;
    private String password;
    private String driver;
    private String dialect;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getDialect() {
        return dialect;
    }

    public void setDialect(String dialect) {
        this.dialect = dialect;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return false;
        if (o == null || getClass() != o.getClass()) return false;
        DatabaseCredentials that = (DatabaseCredentials) o;
        return that.getDriver().equals(this.getDriver()) &&
                that.getHost().equals(this.getHost()) &&
                that.getPort().equals(this.getPort()) &&
                that.getDatabase().equals(this.getDatabase()) &&
                that.getUsername().equals(this.getUsername()) &&
                that.getPassword().equals(this.getPassword()) &&
                that.getDialect().equals(this.getDialect());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getDriver(), this.getHost(), this.getPort(), this.getDatabase(), this.getUsername(), this.getPassword(), this.getDialect());
    }
}
