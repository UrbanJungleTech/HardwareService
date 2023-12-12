package urbanjungletech.hardwareservice.service.datasource;

import jakarta.activation.DataSource;
import urbanjungletech.hardwareservice.model.credentials.DatabaseCredentials;

public interface DataSourceContext {
    void addDataSource(DatabaseCredentials credentials);

    void removeDataSource(DatabaseCredentials credentials);

    void setDataSource(DatabaseCredentials credentials);
}
