package urbanjungletech.hardwareservice.entity.sensorreadingrouter;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import urbanjungletech.hardwareservice.entity.connectiondetails.DatabaseConnectionDetailsEntity;
import urbanjungletech.hardwareservice.entity.credentials.CredentialsEntity;
import urbanjungletech.hardwareservice.model.connectiondetails.DatabaseConnectionDetails;

@Entity
public class DatabaseSensorReadingRouterEntity extends SensorReadingRouterEntity{
    private String tableName;
    private String valueColumn;
    private String timestampColumn;
    @ManyToOne
    private DatabaseConnectionDetailsEntity connectionDetails;

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



    public DatabaseConnectionDetailsEntity getConnectionDetails() {
        return connectionDetails;
    }

    public void setConnectionDetails(DatabaseConnectionDetailsEntity connectionDetails) {
        this.connectionDetails = connectionDetails;
    }

    public String getTimestampColumn() {
        return timestampColumn;
    }

    public void setTimestampColumn(String timestampColumn) {
        this.timestampColumn = timestampColumn;
    }
}
