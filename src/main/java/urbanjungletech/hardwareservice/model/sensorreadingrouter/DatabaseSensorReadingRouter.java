package urbanjungletech.hardwareservice.model.sensorreadingrouter;

import urbanjungletech.hardwareservice.model.connectiondetails.DatabaseConnectionDetails;
import urbanjungletech.hardwareservice.model.credentials.Credentials;
import urbanjungletech.hardwareservice.model.credentials.DatabaseCredentials;

public class DatabaseSensorReadingRouter extends SensorReadingRouter{
    private String tableName;
    private String timestampColumn;
    private String valueColumn;
    private DatabaseConnectionDetails databaseConnectionDetails;


    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTimestampColumn() {
        return timestampColumn;
    }

    public void setTimestampColumn(String timestampColumn) {
        this.timestampColumn = timestampColumn;
    }

    public String getValueColumn() {
        return valueColumn;
    }

    public void setValueColumn(String valueColumn) {
        this.valueColumn = valueColumn;
    }

    public DatabaseConnectionDetails getDatabaseConnectionDetails() {
        return databaseConnectionDetails;
    }

    public void setDatabaseConnectionDetails(DatabaseConnectionDetails databaseConnectionDetails) {
        this.databaseConnectionDetails = databaseConnectionDetails;
    }
}
