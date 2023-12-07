package urbanjungletech.hardwareservice.entity.sensorreadingrouter;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import urbanjungletech.hardwareservice.entity.credentials.CredentialsEntity;

@Entity
public class DatabaseSensorReadingRouterEntity extends SensorReadingRouterEntity{
    private String tableName;
    private String valueColumn;
    private String timestampColumn;
    @ManyToOne
    private CredentialsEntity credentials;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String table) {
        this.tableName = table;
    }

    public String getValueColumn() {
        return valueColumn;
    }

    public void setValueColumn(String valueColumn) {
        this.valueColumn = valueColumn;
    }

    public String getTimestampColumn() {
        return timestampColumn;
    }

    public void setTimestampColumn(String timestampColumn) {
        this.timestampColumn = timestampColumn;
    }

    public CredentialsEntity getCredentials() {
        return credentials;
    }

    public void setCredentials(CredentialsEntity credentials) {
        this.credentials = credentials;
    }
}
